package com.example.tp5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    private AppCompatButton set, next, previous, up, down, tab, click, move, beep;
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

        this.next = this.findViewById(R.id.buttonRight);
        this.next.setOnClickListener(view -> this.onClickAction("next"));

        this.previous = this.findViewById(R.id.buttonLeft);
        this.previous.setOnClickListener(view -> this.onClickAction("previous"));

        this.up = this.findViewById(R.id.buttonUp);
        this.up.setOnClickListener(view -> this.onClickAction("up"));

        this.down = this.findViewById(R.id.buttonDown);
        this.down.setOnClickListener(view -> this.onClickAction("down"));

        this.tab = this.findViewById(R.id.buttonTab);
        this.tab.setOnClickListener(view -> this.onClickAction("tab"));

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
                    Log.d("Set",e.getMessage());
                } catch (InterruptedException e) {
                    Log.d("Set",e.getMessage());
                }
                break;
            case "next":
                this.sender.offer("next\n".getBytes());
            case "previous":
                this.sender.offer("previous\n".getBytes());
            case "up":
                this.sender.offer("up\n".getBytes());
            case "down":
                this.sender.offer("down\n".getBytes());
            case "tab":
                this.sender.offer("tab\n".getBytes());
            case "click":
                this.lastClick = currentClick;
                this.currentClick = System.currentTimeMillis();

                if (this.currentClick - this.lastClick < 250) {
                    this.lastClick = 0;
                    this.currentClick = 0;
                    this.sender.offer("clickDouble\n".getBytes());
                }
                this.sender.offer("click\n".getBytes());
            case "move":
            case "beep":
                this.sender.offer("beep\n".getBytes());
            default:
                Toast toast = Toast.makeText(this, "Send " + string +" !", Toast.LENGTH_SHORT);
                toast.show();
        }
    }

    public class Sender extends Thread {
        private BlockingQueue<byte[]> queue;

        private DataOutputStream out;

        public Sender(DataOutputStream out) {
            queue = new SynchronousQueue<byte[]>();
            this.out = out;
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
                Log.d("Socket", e.getMessage());
            }
            return out;
        }
    }
}