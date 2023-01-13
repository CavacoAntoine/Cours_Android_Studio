package com.example.tp6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private TextView title;
    private EditText nom, quantity;
    private ArrayList<CourseItem> liste;
    private CourseItemAdapter courseItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        this.title = findViewById(R.id.textList);
        Button add = findViewById(R.id.addButton);
        Button buttonBack = findViewById(R.id.backButton);
        this.nom = findViewById(R.id.editNom);
        this.quantity = findViewById(R.id.editQuantity);
        ListView listView = findViewById(R.id.list);

        buttonBack.setOnClickListener(this::goBack);
        add.setOnClickListener(this::clickAjouter);
        listView.setOnItemLongClickListener(this::longItemClick);
        listView.setOnItemClickListener(this::itemClick);


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

    private void goBack(View view) {
        this.saveList();
        finish();
    }

    private void itemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(this.liste.get(i).getQuantité() == 1){
            this.longItemClick(adapterView,view,i,l);
            return;
        }
        this.liste.get(i).decremente();
        this.courseItemAdapter.notifyDataSetChanged();
    }

    private void saveList() {
        StringBuilder sharedListe = new StringBuilder();
        for( CourseItem item : this.liste) {
            sharedListe.append(item.getNom()).append(",").append(item.getQuantité()).append(",");
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(this.title.getText().toString(), sharedListe.toString());
        edit.apply();
    }

    private boolean loadListe(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedListe = prefs.getString(this.title.getText().toString(), null);
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
        String quantity = this.quantity.getText().toString();
        if(nom.isEmpty() || quantity.isEmpty())
            return;
        this.liste.add(new CourseItem(nom, Integer.parseInt(quantity)));
        this.courseItemAdapter.notifyDataSetChanged();
        this.nom.setText("");
        this.quantity.setText("");

    }

    private boolean longItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.liste.remove(position);
        this.courseItemAdapter.notifyDataSetChanged();
        saveList();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.saveList();
    }
}