package com.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private EditText ip, port, x, y;
    private Button set, next, previous, up, down, tab, click, move, beep;
    private long lastClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.set.setOnClickListener(view -> this.onClickAction(new String[]{"set",
                this.ip.getText().toString(), this.port.getText().toString()}));

        this.next.setOnClickListener(view -> this.onClickAction(new String[]{"next"}));
        this.previous.setOnClickListener(view -> this.onClickAction(new String[]{"previous"}));
        this.up.setOnClickListener(view -> this.onClickAction(new String[]{"up"}));
        this.down.setOnClickListener(view -> this.onClickAction(new String[]{"down"}));
        this.tab.setOnClickListener(view -> this.onClickAction(new String[]{"tab"}));
        this.click.setOnClickListener(view -> this.onClickAction(new String[]{"click"}));

    }

    private void onClickAction(String[] strings){
        switch (strings[0]) {
            case "set":
                break;
            case "next":
                break;
            case "previous":
                break;
            case "up":
                break;
            case "down":
                break;
            case "tab":
                break;
            case "click":
                break;
            case "move":
                break;
            case "beep":
                break;
            default:
                return ;
        }
    }

    private class TCPAction extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            while(!isCancelled()){
                try(Socket socket = new Socket("10.0.0.2.2", 80)) {
                    DataOutputStream out= new DataOutputStream( socket.getOutputStream());

                    out.write(strings[0].getBytes());
                    out.write("\n".getBytes());
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
            return null;
        }
    }
}