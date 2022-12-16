package com.example.tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class MainActivity extends AppCompatActivity {

    private EditText ip, port, x, y;
    private Button set, next, previous, up, down, tab, click, move, beep;
    private long lastClick, currentClick;
    private DataOutputStream out;
    private Socket socket;
    private Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sender = new Sender();
        this.lastClick = -1;

        this.ip = this.findViewById(R.id.inputIP);
        this.port = this.findViewById(R.id.inputPort);

        this.set = this.findViewById(R.id.buttonSet);
        this.set.setOnClickListener(view -> this.onClickAction(new String[]{"set",
                this.ip.getText().toString(), this.port.getText().toString()}));

        //this.next.setOnClickListener(view -> this.onClickAction(new String[]{"next"}));
        //this.previous.setOnClickListener(view -> this.onClickAction(new String[]{"previous"}));
        //this.up.setOnClickListener(view -> this.onClickAction(new String[]{"up"}));
        //this.down.setOnClickListener(view -> this.onClickAction(new String[]{"down"}));
        //this.tab.setOnClickListener(view -> this.onClickAction(new String[]{"tab"}));
        this.beep = this.findViewById(R.id.buttonBeep);
        this.beep.setOnClickListener(view -> this.onClickAction(new String[]{"beep"}));
        this.click = this.findViewById(R.id.buttonClick);
        this.click.setOnClickListener(view -> this.onClickAction(new String[]{"click"}));
    }

    private void onClickAction(String[] strings){
        switch (strings[0]) {
            case "set":
                SocketInitalize socketInitalize = new SocketInitalize();
                socketInitalize.execute(this.ip.getText().toString(), this.port.getText().toString());
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
                this.sender.offer("beep".getBytes());
                break;
            default:
                return ;
        }
    }

    public class Sender extends Thread {
        private BlockingQueue<byte[]> queue;

        public Sender() {
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
                }
            }
        }

    }

    private class SocketInitalize extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                socket = new Socket(strings[0], Integer.parseInt(strings[1]));
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                Log.e("Socket", e.getMessage());
            }
            return null;
        }
    }
}