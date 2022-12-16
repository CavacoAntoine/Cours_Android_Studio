package com.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;

public class MainActivity extends AppCompatActivity {

    private EditText ip, port, x, y;
    private Button set, next, previous, up, down, tab, click, move, beep;
    private long lastClick, currentClick;
    private Socket socket;
    private Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.lastClick = -1;

        this.ip = this.findViewById(R.id.inputIP);
        this.port = this.findViewById(R.id.inputPort);

        this.set = this.findViewById(R.id.buttonSet);
        this.set.setOnClickListener(view -> this.onClickAction("set"));

        //this.next.setOnClickListener(view -> this.onClickAction(new String[]{"next"}));
        //this.previous.setOnClickListener(view -> this.onClickAction(new String[]{"previous"}));
        //this.up.setOnClickListener(view -> this.onClickAction(new String[]{"up"}));
        //this.down.setOnClickListener(view -> this.onClickAction(new String[]{"down"}));
        //this.tab.setOnClickListener(view -> this.onClickAction(new String[]{"tab"}));
        this.beep = this.findViewById(R.id.buttonBeep);
        this.beep.setOnClickListener(view -> this.onClickAction("beep"));
        this.click = this.findViewById(R.id.buttonClick);
        this.click.setOnClickListener(view -> this.onClickAction("click"));
    }

    private void onClickAction(String string){
        switch (string) {
            case "set":
                SocketInitalize socketInitalize = new SocketInitalize();
                socketInitalize.execute(this.ip.getText().toString(), this.port.getText().toString());
                try {
                    this.sender = new Sender(socketInitalize.get());
                    Toast toast = Toast.makeText(this, "Connexion effectué !", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "next":
                this.sender.offer("next".getBytes());
                break;
            case "previous":
                this.sender.offer("previous".getBytes());
                break;
            case "up":
                this.sender.offer("up".getBytes());
                break;
            case "down":
                this.sender.offer("down".getBytes());
                break;
            case "tab":
                this.sender.offer("tab".getBytes());
                break;
            case "click":
                this.lastClick = currentClick;
                this.currentClick = System.currentTimeMillis();

                if (this.currentClick - this.lastClick < 250) {
                    this.lastClick = 0;
                    this.currentClick = 0;
                    this.sender.offer("clickDouble".getBytes());
                }
                this.sender.offer("click".getBytes());
                break;
            case "move":
                break;
            case "beep":
                this.sender.offer("Antoine le boss".getBytes());
                Toast toast = Toast.makeText(this, "Send beep !", Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                return ;
        }
    }

    public class Sender extends Thread {
        private BlockingQueue<byte[]> queue;

        private DataOutputStream out;

        public Sender(DataOutputStream out) {
            queue = new SynchronousQueue<byte[]>();
            this.start();
        }

        public boolean offer(byte[] cmd) {
            return queue.offer(cmd);
        }

        public void close() {
            this.interrupt();
        }

        public void run() {
            while (!isInterrupted()) {
                try {
                    byte[] cmd = queue.take();
                    out.write(cmd);
                } catch (Exception e) {
                    Log.d("Sender", e.getMessage());
                }
            }
        }

    }

    private class SocketInitalize extends AsyncTask<String, Void, DataOutputStream> {

        @Override
        protected DataOutputStream doInBackground(String... strings) {
            DataOutputStream out = null;
            try {
                socket = new Socket(strings[0], Integer.parseInt(strings[1]));
                Log.d("Socket", strings[0]);
                Log.d("Socket", strings[1]);
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                Log.e("Socket", e.getMessage());
            }
            return out;
        }
    }
}