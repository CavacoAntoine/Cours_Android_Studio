package com.example.tp4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private HumiditySensorAbstract ds2438;
    private AppCompatButton buttonStart, buttonStop, buttonSet;
    private TextView textView;
    private EditText editText;
    private ProgressBar progressBar;
    private HumidityAssynTask humidityAssynTask;
    private View view;
    private boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editText = findViewById(R.id.editText);
        String url = this.loadURL_SENSOR();
        if(url == null) {
            this.ds2438 = new HTTPHumiditySensor();
        } else {
            this.ds2438 = new HTTPHumiditySensor(url);
            this.editText.setText(url);
        }

        this.buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(view -> this.start());
        this.buttonStop = findViewById(R.id.buttonStop);
        this.buttonStop.setClickable(false);
        buttonStop.setOnClickListener(view -> this.stop());
        this.buttonSet = findViewById(R.id.buttonSet);
        this.buttonSet.setVisibility(View.GONE);
        this.buttonSet.setOnClickListener(view -> this.setUrl());
        this.textView = findViewById(R.id.text);
        textView.setText("acquisition..., cliquez sur start");
        this.progressBar = findViewById(R.id.progress);
        this.view = findViewById(R.id.view);
        this.view.setOnClickListener(view -> this.setEdit());
    }

    private void start() {
        this.humidityAssynTask = new HumidityAssynTask();
        this.buttonStop.setClickable(true);
        this.buttonStart.setClickable(false);
        humidityAssynTask.execute();
        this.isStart = true;
    }

    private void stop() {
        if(this.humidityAssynTask != null) {
            this.humidityAssynTask.cancel(true);
        }
        this.isStart = false;
    }

    private void setEdit() {
        this.view.setVisibility(View.GONE);
        this.stop();
        this.buttonSet.setVisibility(View.VISIBLE);
    }

    private void setUrl() {
        String url = this.editText.getText().toString();
        if(!url.equals("")){
            this.ds2438.setUrlSensor(url);
            this.saveURL_SENSOR(url);
        }
        this.buttonSet.setVisibility(View.GONE);
        this.view.setVisibility(View.VISIBLE);
    }

    private void saveURL_SENSOR(String url) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("url_sensor", url);
        edit.commit();
    }

    private String loadURL_SENSOR(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("url_sensor", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.isStart) {
            this.stop();
            this.isStart = true;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isStart", this.isStart );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.isStart = savedInstanceState.getBoolean("isStart");
        if(this.isStart) {
            this.start();
        }
    }

    private class HumidityAssynTask extends AsyncTask<Void, Float, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            while(!isCancelled()) {
                try {
                    publishProgress(ds2438.value());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressBar.setProgress(0);
            buttonStart.setClickable(true);
            buttonStop.setClickable(false);
            if(!aBoolean) {
                textView.setText("Quelque chose s'est mal passé");
                return;
            }
            textView.setText("acquisition..., cliquez sur start");
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            super.onProgressUpdate(values);

            Time currentTime = new Time();
            currentTime.setToNow();
            String date = currentTime.format("%d.%m.%Y %H:%M:%S");
            textView.setText("[" + date + "] ds2438 : " + values[0]);
            progressBar.setProgress(values[0].intValue());
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
            progressBar.setProgress(0);
            buttonStart.setClickable(true);
            buttonStop.setClickable(false);
            textView.setText("acquisition..., cliquez sur start");
        }

    }

}