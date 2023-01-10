package com.example.tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button ajouter;
    private ListView listeView;
    private ArrayList<String> items;
    private ArrayAdapter<String> stringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.stringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listeView.setAdapter(this.stringAdapter);

        this.ajouter.setOnClickListener(this::clickAjouter);

        this.listeView.setOnItemLongClickListener(this::longItemClick);
    }

    void clickAjouter(View view) {
        String item = this.editText.getText().toString();
        this.items.add(item);
        this.stringAdapter.notifyDataSetChanged();
    }

    boolean longItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.items.remove(position);
        this.stringAdapter.notifyDataSetChanged();
        return false;
    }
}