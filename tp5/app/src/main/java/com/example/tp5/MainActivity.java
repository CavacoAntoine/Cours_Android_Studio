package com.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText ip, port, x, y;
    private Button set, next, previous, up, down, tab, click, clickDouble, move, beep, mousePress, mouseRelease, getScreenSize, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}