package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart, buttonStop, buttonSet;
    private TextView textView;
    private ProgressBar progressBar;
    private HumidityAssynTask humidityAssynTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(view -> this.start());
        this.buttonStop = findViewById(R.id.buttonStop);
        this.buttonStop.setClickable(false);
        buttonStop.setOnClickListener(view -> this.stop());
        this.buttonSet = findViewById(R.id.buttonSet);
        this.textView = findViewById(R.id.text);
        textView.setText("acquisition..., cliquez sur start");
        this.progressBar = findViewById(R.id.progress);
    }

    private void start(){
        this.humidityAssynTask = new HumidityAssynTask();
        this.buttonStop.setClickable(true);
        this.buttonStart.setClickable(false);
        humidityAssynTask.execute();
    }

    private void stop(){
        if(this.humidityAssynTask != null) {
            this.humidityAssynTask.cancel(true);
        }
    }

    private class HumidityAssynTask extends AsyncTask<Void, Float, Boolean> {

        HumiditySensorAbstract ds2438 = new HTTPHumiditySensor();

        @Override
        protected Boolean doInBackground(Void... voids) {
            while(!isCancelled()) {
                try {
                    publishProgress(ds2438.value());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Log.d("pouet", e.getMessage());
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