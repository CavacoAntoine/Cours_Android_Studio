package com.example.tp3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonCall, buttonWeb, buttonLogin;
    private EditText editTextCall;
    private ActivityResultLauncher<Intent> loginLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loginLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Toast toast = Toast.makeText(this, "Login : " + data.getStringExtra("login") + "\nPassword : " + data.getStringExtra("password"), Toast.LENGTH_LONG);
                toast.show();
            } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                Toast toast = Toast.makeText(this, "Opération annulé", Toast.LENGTH_LONG);
                toast.show();
            }
        });


        //0770167488
        this.editTextCall = findViewById(R.id.editTextCall);

        this.buttonCall = findViewById(R.id.buttonCall);
        this.buttonCall.setOnClickListener(view -> this.call());

        this.buttonWeb = findViewById(R.id.buttonWeb);
        this.buttonWeb.setOnClickListener(view -> this.web());

        this.buttonLogin = findViewById(R.id.buttonLogin);
        this.buttonLogin.setOnClickListener(view -> this.login());

    }

    private void call() {
        if (Autorisation.callPermission(this)) {
            String url = this.editTextCall.getText().toString();
            Log.d("oioioi", url);
            if (!url.matches("(\\+)?\\d+")) {
                //Entrez un numéro de téléphone valide
                return;
            }
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + url));
            startActivity(intent);
        }
    }

    private void web() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.ecosia.org"));
        startActivity(intent);
    }

    private void login() {
        if (Autorisation.loginPermission(this)) {
            Intent intent = new Intent("APPLI_LOGIN");
            this.loginLauncher.launch(intent);
        } else {
            Toast toast = Toast.makeText(this, "Vous n'avez pas la permission de faire ceci.", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tel", this.editTextCall.getText().toString() );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.editTextCall.setText(savedInstanceState.getString("tel"));
    }
}