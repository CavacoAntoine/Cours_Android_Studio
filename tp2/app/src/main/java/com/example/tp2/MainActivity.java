package com.example.tp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.tp2.calculette.Calculette;
import com.example.tp2.calculette.CalculetteException;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    Calculette calculette;
    AppCompatTextView affichage, erreurs;
    EditText editText;
    AppCompatButton buttonPile, buttonPlus, buttonMinus, buttonMult, buttonDiv, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calculette = new Calculette();
        calculette.addObserver(this);

        this.affichage = findViewById(R.id.screen);
        this.editText = findViewById(R.id.input);

        this.buttonPile = findViewById(R.id.buttonPile);
        buttonPile.setOnClickListener(view -> this.empile());

        this.buttonPlus = findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(view -> this.add());

        this.buttonMinus = findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(view -> this.sub());

        this.buttonMult = findViewById(R.id.buttonMult);
        buttonMult.setOnClickListener(view -> this.mul());

        this.buttonDiv = findViewById(R.id.buttonDiv);
        buttonDiv.setOnClickListener(view -> this.div());

        this.buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(view -> this.clear());

    }

    public void empile(){
        try{
            this.calculette.enter(Integer.parseInt(this.editText.getText().toString()));
        } catch (NumberFormatException | CalculetteException e) {
            this.erreurs.setText(e.getMessage());
        }
    }

    public void add(){
        try {
            this.calculette.add();
        } catch (CalculetteException e) {
            this.erreurs.setText(e.getMessage());
        }
    }

    public void sub(){
        try {
            this.calculette.sub();
        } catch (CalculetteException e) {
            this.erreurs.setText(e.getMessage());
        }
    }

    public void mul(){
        try {
            this.calculette.mul();
        } catch (CalculetteException e) {
            this.erreurs.setText(e.getMessage());
        }
    }

    public void div(){
        try {
            this.calculette.div();
        } catch (CalculetteException e) {
            this.erreurs.setText(e.getMessage());
        }
    }

    public void clear(){
        this.calculette.clear();
    }

    @Override
    public void update(Observable observable, Object o) {
        this.affichage.setText(this.calculette.toString());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        int[] status = new int[this.calculette.size()];
        for(int i = 0; i<this.calculette.size(); i++) {
            try {
                status[i] = this.calculette.pop();
            } catch (CalculetteException e) {
                break;
            }
        }
        outState.putIntArray("calculette", status);

    }
}