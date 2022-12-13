package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button start,stop;
    private TextView output;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.start = findViewById(R.id.buttonStart);
        this.stop = findViewById(R.id.buttonStop);
        this.output = findViewById(R.id.text);
        this.progress = findViewById(R.id.progress);

        this.start.setOnClickListener(view ->
        {
            
        });
    }
}