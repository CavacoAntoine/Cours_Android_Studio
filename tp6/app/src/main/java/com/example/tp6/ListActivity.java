package com.example.tp6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private TextView title;
    private ListView listView;
    private Button add;
    private EditText nom, quantity;
    private ImageView micro;
    private ArrayList<CourseItem> liste;
    private CourseItemAdapter courseItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);


        this.title = findViewById(R.id.textList);
        this.add = findViewById(R.id.addButton);
        this.nom = findViewById(R.id.editNom);
        this.quantity = findViewById(R.id.editQuantity);
        this.listView = findViewById(R.id.list);



        this.add.setOnClickListener(this::clickAjouter);
        this.listView.setOnItemLongClickListener(this::longItemClick);
        this.listView.setOnItemClickListener(this::itemClick);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.title.setText(extras.getString("nom"));
        }

        if(!this.loadListe()){
            this.liste = new ArrayList<>();
        }

        this.courseItemAdapter = new CourseItemAdapter(this, this.liste);
        listView.setAdapter(this.courseItemAdapter);
    }

    private void itemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.liste.get(i).decremente();
        this.courseItemAdapter.notifyDataSetChanged();
    }

    private void saveList() {
        String sharedListe = "";
        for( CourseItem item : this.liste) {
            sharedListe+= item.getNom() + "," + item.getQuantité() + ",";
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("items", sharedListe);
        edit.apply();
    }

    private boolean loadListe(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedListe = prefs.getString("items", null);
        if(sharedListe == null)
            return false;

        String[] items = sharedListe.split(",");

        this.liste = new ArrayList<>();
        for(int i = 0; i<items.length-1; i++) {
            this.liste.add(new CourseItem(items[i],Integer.parseInt(items[i+1])));
        }
        return true;
    }

    private void clickAjouter(View view) {
        String nom = this.nom.getText().toString();
        int quantity = Integer.parseInt(this.quantity.getText().toString());
        if(nom.isEmpty())
            return;
        this.liste.add(new CourseItem(nom, quantity));
        this.courseItemAdapter.notifyDataSetChanged();
    }

    private boolean longItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.liste.remove(position);
        this.courseItemAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.saveList();
    }
}