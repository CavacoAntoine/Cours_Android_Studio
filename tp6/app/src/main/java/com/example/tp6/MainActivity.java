package com.example.tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button addButton;
    private ListView listView;
    private ArrayList<String> listes;
    private ArrayAdapter<String> stringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!this.loadListes()) {
            this.listes = new ArrayList<>();
        }

        this.addButton = findViewById(R.id.addButton);
        this.addButton.setOnClickListener(this::clickAjouter);

        this.listView = findViewById(R.id.list);
        this.stringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listes);
        listView.setAdapter(this.stringAdapter);
        this.listView.setOnItemLongClickListener(this::longItemClick);
        this.listView.setOnItemClickListener(this::itemClick);

        this.editText = findViewById(R.id.editText);
    }

    private void itemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.saveList();
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("nom", this.listes.get(i));
        startActivity(intent);
    }

    private void saveList() {
        String sharedListe = "";
        for( String liste : this.listes) {
            sharedListe+= liste + ",";
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("NOMLISTE", sharedListe);
        edit.apply();
    }

    private boolean loadListes(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedListes = prefs.getString("NOMLISTE", null);
        if(sharedListes == null)
            return false;

        String[] noms = sharedListes.split(",");

        this.listes = new ArrayList<>();
        for(String nom : noms) {
            this.listes.add(nom);
        }
        return true;
    }

    private void clickAjouter(View view) {
        String nom = this.editText.getText().toString();
        if(nom.isEmpty())
            return;
        this.listes.add(nom);
        this.stringAdapter.notifyDataSetChanged();
        this.editText.setText("");
    }

    private boolean longItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedListe = prefs.getString(this.listes.get(position), null);
        if(sharedListe != null) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.remove(this.listes.get(position));
            edit.apply();
        }
        this.listes.remove(position);
        this.stringAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.saveList();
    }
}