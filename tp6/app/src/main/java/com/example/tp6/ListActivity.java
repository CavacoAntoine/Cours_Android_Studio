package com.example.tp6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    private TextView title;
    private ListView listView;
    private Button add;
    private EditText nom, quantity;
    private ImageView micro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        this.title = findViewById(R.id.textList);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.title.setText(extras.getString("nom"));
        }
    }
}