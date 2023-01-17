package com.example.tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText, editLat, editLong;
    private ArrayList<ListItem> listes;
    private ListItemAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listes = new ArrayList<>();
        this.loadListes();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this::clickAjouter);

        ListView listView = findViewById(R.id.list);
        this.listItemAdapter = new ListItemAdapter(this, this.listes);
        listView.setAdapter(this.listItemAdapter);
        listView.setOnItemLongClickListener(this::longItemClick);
        listView.setOnItemClickListener(this::itemClick);

        this.editText = findViewById(R.id.editText);
        this.editLat = findViewById(R.id.editLat);
        this.editLong = findViewById(R.id.editLong);
    }

    private void itemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.saveList();
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("nom", this.listes.get(i).getNom());
        intent.putExtra("lat", ""+this.listes.get(i).getLatitude());
        intent.putExtra("long", ""+this.listes.get(i).getLongitude());
        startActivity(intent);
    }

    private void saveList() {
        StringBuilder sharedListe = new StringBuilder();
        for( ListItem liste : this.listes) {
            sharedListe.append(liste.getNom()).append(",").append(liste.getLatitude()).append(",").append(liste.getLongitude()).append(",");
        }

        SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.putString("NOMLISTE", sharedListe.toString());
        edit.apply();
    }

    private boolean loadListes(){
        SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
        String sharedListes = prefs.getString("NOMLISTE", null);
        if(sharedListes == null){
            return false;
        }

        String[] items = sharedListes.split(",");

        for(int i = 0; i < items.length-2; i+=3) {
            this.listes.add(new ListItem(items[i], Double.parseDouble(items[i+1]), Double.parseDouble(items[i+2])));
        }
        return true;
    }

    private void clickAjouter(View view) {
        String nom = this.editText.getText().toString();
        if (nom.isEmpty())
            return;
        ListItem newItem = new ListItem(nom);
        if(!this.listes.contains(newItem)) {
            String sLat = this.editLat.getText().toString();
            String sLong = this.editLong.getText().toString();
            if (sLat.isEmpty()) {
                sLat = "0";
            }
            if (sLong.isEmpty()) {
                sLong = "0";
            }
            newItem.setLatitude(Double.parseDouble(sLat));
            newItem.setLongitude(Double.parseDouble(sLong));
            this.listes.add(newItem);
            this.listItemAdapter.notifyDataSetChanged();
        }else {
            Toast toast = Toast.makeText(this, "Cette liste existe déjà.", Toast.LENGTH_SHORT);
            toast.show();
        }
        this.editText.setText("");
        this.editLat.setText("");
        this.editLong.setText("");
    }

    private boolean longItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        SharedPreferences shared = this.getSharedPreferences(ListActivity.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.remove(this.listes.get(position).getNom());
        edit.apply();
        this.listes.remove(position);
        this.listItemAdapter.notifyDataSetChanged();
        this.saveList();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.saveList();
    }
}