package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    long selectedTime;
    final int ms = 1000;
    String timeString;
    String pad;
    int cntr;
    long saved_time;
    CountDownTimer cdt;


    TextView timerText;
    SeekBar timerSeekBar;
    Button startStopButton;
    MediaPlayer mp;


    public long[] msToMin(long ms){
        long[] rtn = {
                (ms/1000) / 60,
                (ms/1000) % 60
        };
        return rtn;
    }

    public void createCountDownTimer(long t){
        cdt = new CountDownTimer(t,ms){
            public void onTick(long l ){
                Log.i("log", String.valueOf(l/ms));

                long[] time = msToMin(l);
                if(time[1] < 10){
                    pad = "0";
                }else{
                    pad = "";
                }
                saved_time = l;
                timeString = String.valueOf(time[0]) + ":" + pad + String.valueOf(time[1]);
                timerText.setText(timeString);
                timerSeekBar.setProgress((int)l/ms);

            }
            public void onFinish(){
                Log.i("log", "complete");
                mp.start();
                timerText.setText("Eggs are ready!");
                startStopButton.setText("Start");
                cntr = 0;
                saved_time = 0;
                timerSeekBar.setEnabled(true);
            }
        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timerText);
        timerSeekBar = findViewById(R.id.seekBar);
        startStopButton = findViewById(R.id.startStopButton);
        timerSeekBar.setMax(1200);
        mp = MediaPlayer.create(this, R.raw.alarm);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedTime = progress;
                long[] time = msToMin(progress*1000);
                if(time[1] < 10){
                    pad = "0";
                }else{
                    pad = "";
                }
                timeString = String.valueOf(time[0]) + ":" + pad + String.valueOf(time[1]);
                timerText.setText(timeString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cntr %2 == 0){
                    startStopButton.setText("Pause");
                    createCountDownTimer(selectedTime*1000);
                    cntr +=1;
                    timerSeekBar.setEnabled(false);

                }else{
                    cdt.cancel();
                    startStopButton.setText("Start");
                    cntr +=1;
                    timerSeekBar.setEnabled(true);
                }

            }
        });


    }
}
