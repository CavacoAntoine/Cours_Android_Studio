package com.example.tp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.tp2.calculette.Calculette;
import com.example.tp2.calculette.CalculetteException;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private Calculette calculette;
    private AppCompatTextView affichage, erreurs;
    private EditText editText;
    private AppCompatButton buttonPile, buttonPlus, buttonMinus, buttonMult, buttonDiv, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calculette = new Calculette();
        calculette.addObserver(this);

        this.affichage = findViewById(R.id.screen);
        this.editText = findViewById(R.id.input);

        this.erreurs = findViewById(R.id.error);

        this.buttonPile = findViewById(R.id.buttonPile);
        this.buttonPile.setOnClickListener(view -> this.empile());

        this.buttonPlus = findViewById(R.id.buttonPlus);
        this.buttonPlus.setOnClickListener(view -> this.add());

        this.buttonMinus = findViewById(R.id.buttonMinus);
        this.buttonMinus.setOnClickListener(view -> this.sub());

        this.buttonMult = findViewById(R.id.buttonMult);
        this.buttonMult.setOnClickListener(view -> this.mul());

        this.buttonDiv = findViewById(R.id.buttonDiv);
        this.buttonDiv.setOnClickListener(view -> this.div());

        this.buttonClear = findViewById(R.id.buttonClear);
        this.buttonClear.setOnClickListener(view -> this.clear());
    }

    public void empile(){
        try{
            this.calculette.enter(Integer.parseInt(this.editText.getText().toString()));
        } catch (NumberFormatException e) {
            this.showError(this.editText.getText().toString() + " n'est pas nombre");
        } catch (CalculetteException e) {
            this.showError(e.getMessage());
        }
    }

    public void add(){
        try {
            this.calculette.add();
        } catch (CalculetteException e) {
            this.showError(e.getMessage());

        }
    }

    public void sub(){
        try {
            this.calculette.sub();
        } catch (CalculetteException e) {
            this.showError(e.getMessage());
        }
    }

    public void mul(){
        try {
            this.calculette.mul();
        } catch (CalculetteException e) {
            this.showError(e.getMessage());
        }
    }

    public void div(){
        try {
            this.calculette.div();
        } catch (CalculetteException e) {
            this.showError(e.getMessage());
        }
    }

    public void clear(){
        this.calculette.clear();
    }

    public void showError(String message){
        this.erreurs.setText(message);
        this.erreurs.setVisibility(View.VISIBLE);

        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS}, 1);
                return;
            }
        }

        SmsManager sm = SmsManager.getDefault();
        String body = getString(R.string.app_name) + " : " + message + "\n";
        sm.sendTextMessage("5554", null, body, null, null);

    }

    @Override
    public void update(Observable observable, Object o) {
        this.affichage.setText(this.calculette.toString());
        this.editText.setText("");
        this.erreurs.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        int taille = this.calculette.size();

        outState.putInt("size", taille );

        int[] status = new int[taille];
        for(int i = 0; i<taille; i++) {
            try {
                status[i] = this.calculette.pop();
            } catch (CalculetteException e) {
                break;
            }
        }

        outState.putIntArray("calculette", status);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int size = savedInstanceState.getInt("size");
        int[] status = savedInstanceState.getIntArray("calculette");

        for(int i = size-1; i>= 0 ; i--){
            try {
                this.calculette.enter(status[i]);
            } catch (CalculetteException e) {
                break;
            }
        }
    }
}