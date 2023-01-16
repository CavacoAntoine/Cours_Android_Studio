package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView log;
    private int numero;
    private boolean isFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.isFinish = false;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            numero = bundle.getInt("numero");
            numero++;
        }

        log = findViewById(R.id.log);
        this.log.setMovementMethod(new ScrollingMovementMethod());
        this.load();
        log.append("onCreate " + numero + "\n");

        AppCompatButton start = findViewById(R.id.start);
        start.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("numero", numero);
            startActivity(intent);
        });

        AppCompatButton finish = findViewById(R.id.finish);
        finish.setOnClickListener(this::clear);

    }

    private void save(){
        if(!this.isFinish) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("LOGS" + this.numero, this.log.getText().toString());
            edit.apply();
        }
    }

    private void load() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedLogs = prefs.getString("LOGS" + this.numero, null);
        if(sharedLogs != null) {
            this.log.setText(sharedLogs);
        }
    }

    private void clear(View view) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();
        this.log.setText("");
        this.isFinish = true;
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        log.append("onStart\n");
        this.save();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log.append("onRestart\n");
        this.save();
    }

    @Override
    protected void onStop() {
        super.onStop();
        log.append("onStop\n");
        this.save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log.append("onDestroy\n");
        this.save();
    }

    @Override
    protected void onPause() {
        super.onPause();
        log.append("onPause\n");
        this.save();
    }

    @Override
    protected void onResume() {
        super.onResume();
        log.append("onResume\n");
        this.save();
    }

}