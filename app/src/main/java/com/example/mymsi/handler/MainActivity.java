package com.example.mymsi.handler;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView)findViewById(R.id.textView);
        textView.setTextColor(Color.GREEN);
        Button buttonStart = (Button)findViewById(R.id.buttonStart);
        final Handler handler = new Handler(){
            public void handleMessage(Message message){
                textView.setText(message.arg1 + "");
            }
        };
        final Runnable newWorker = new Runnable() {

            @Override
            public void run() {
                int progress = 0;
                while(progress<=100){
                    Message message = new Message();
                    message.arg1 = progress;
                    handler.sendMessage(message);
                    progress+= 5;
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                Message message = handler.obtainMessage();
                message.arg1 = -1;
                handler.sendMessage(message);
            }
        };
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, newWorker, "WorkThread");
                workThread.start();
            }
        });
    }
}
