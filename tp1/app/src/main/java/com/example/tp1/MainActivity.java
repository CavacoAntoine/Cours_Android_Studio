package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton appCompatButton = findViewById(R.id.button_TP1);
        appCompatButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, TP1_Activity.class);
            startActivity(intent);
        });

    }

}