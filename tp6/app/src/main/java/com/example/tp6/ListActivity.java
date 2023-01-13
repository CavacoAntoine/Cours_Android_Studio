package com.example.tp6;

import static android.content.Intent.ACTION_VIEW;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private TextView title;
    private EditText nom, quantity;
    private ImageView micro;
    private ArrayList<CourseItem> liste;
    private CourseItemAdapter courseItemAdapter;
    private ActivityResultLauncher<Intent> recognizeSpeech;
    private ListItem listeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        this.recognizeSpeech = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onActivityResult);

        this.title = findViewById(R.id.textList);
        Button add = findViewById(R.id.addButton);
        Button buttonBack = findViewById(R.id.backButton);
        this.nom = findViewById(R.id.editNom);
        this.quantity = findViewById(R.id.editQuantity);
        ListView listView = findViewById(R.id.list);
        this.micro = findViewById(R.id.micro);

        buttonBack.setOnClickListener(this::goBack);
        add.setOnClickListener(this::clickAjouter);
        listView.setOnItemLongClickListener(this::longItemClick);
        listView.setOnItemClickListener(this::itemClick);
        this.micro.setOnClickListener(this::onClickSpeechRecognition);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String nom = extras.getString("nom");
            String sLat = extras.getString("lat");
            String sLong = extras.getString("long");
            this.title.setText(nom);
            this.listeItem = new ListItem(extras.getString("nom"), Double.parseDouble(sLat), Double.parseDouble(sLong));
            if(!(sLat.equals("0") && sLong.equals("0"))){
                this.title.setOnClickListener(this::goOnGoogleMap);
            }
        }

        if(!this.loadListe()){
            this.liste = new ArrayList<>();
        }

        this.courseItemAdapter = new CourseItemAdapter(this, this.liste);
        listView.setAdapter(this.courseItemAdapter);
    }

    public void onClickSpeechRecognition(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR"); // FR imposé
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Dictez le nom du produit...");
        try {
            this.recognizeSpeech.launch(i);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Désolé, vous ne devez pas avoir l'option ...", Toast.LENGTH_LONG).show();
        }
    }

    public void goOnGoogleMap(View v) {
        this.saveList();
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+this.listeItem.getLatitude()+","+this.listeItem.getLongitude());
        Intent intent = new Intent(ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
    }

    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            assert result.getData() != null;
            List<String> speech = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            nom.setText(speech.get(0).trim().toLowerCase());
        }
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