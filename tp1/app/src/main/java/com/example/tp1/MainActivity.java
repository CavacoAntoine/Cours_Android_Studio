package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView log;
    private int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            numero = bundle.getInt("numero");
            numero++;
        }

        log = findViewById(R.id.log);
        log.append("onCreate " + numero + "\n");

        Button start = findViewById(R.id.start);
        start.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("numero", numero);
            startActivity(intent);
        });

        Button finish = findViewById(R.id.finish);
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