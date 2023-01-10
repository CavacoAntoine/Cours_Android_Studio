package com.example.tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button addButton;
    private ListView listView;
    private ArrayList<listeCourse> listes;
    private listCourseAdapter stringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listes = new ArrayList<>();

        this.addButton = findViewById(R.id.addButton);
        this.addButton.setOnClickListener(this::clickAjouter);

        this.listView = findViewById(R.id.list);
        this.stringAdapter = new listCourseAdapter(this, listes);
        listView.setAdapter(this.stringAdapter);
        this.listView.setOnItemLongClickListener(this::longItemClick);

        this.editText = findViewById(R.id.editText);
    }

    void clickAjouter(View view) {
        String nom = this.editText.getText().toString();
        if(nom.isEmpty())
            return;
        this.listes.add(new listeCourse(nom));
        this.stringAdapter.notifyDataSetChanged();
    }

    boolean longItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.listes.remove(position);
        this.stringAdapter.notifyDataSetChanged();
        return false;
    }
}