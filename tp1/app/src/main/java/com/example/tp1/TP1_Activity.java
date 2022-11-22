package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TP1_Activity extends AppCompatActivity {

    private TextView log;
    private int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp1);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            numero = bundle.getInt("numero");
            numero++;
        }

        log = findViewById(R.id.log);
        log.append("onCreate " + numero + "\n");

        AppCompatButton start = findViewById(R.id.start);
        start.setOnClickListener(view -> {
            Intent intent = new Intent(this, TP1_Activity.class);
            intent.putExtra("numero", numero);
            startActivity(intent);
        });

        AppCompatButton finish = findViewById(R.id.finish);
        finish.setOnClickListener(view -> {
            finish();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        log.append("onStart\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log.append("onRestart\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log.append("onStop\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log.append("onDestroy\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log.append("onPause\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log.append("onResume\n");
    }
}