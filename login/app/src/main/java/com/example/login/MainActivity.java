package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonValider, buttonAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonValider = findViewById(R.id.valider);
        this.buttonValider.setOnClickListener(view -> this.valider());

        this.buttonAnnuler = findViewById(R.id.annuler);
        this.buttonAnnuler.setOnClickListener(view -> this.annuler());

    }

    private void valider() {
        Intent intent = new Intent();
        intent.putExtra("login", "ouicmoi");
        intent.putExtra("password","1234");
        setResult(RESULT_OK, intent);
        finish();
    }

    private void annuler() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

}