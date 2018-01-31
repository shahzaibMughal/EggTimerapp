package com.example.shahzaib.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button startStopBtn;
    TextView timerTV;
    CountDownTimer countDownTimer;
    MediaPlayer  myMediaPlayer;
    int seconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        timerTV = findViewById(R.id.timerTV);
        startStopBtn = findViewById(R.id.startStopBtn);
        myMediaPlayer = MediaPlayer.create(this,R.raw.air_horn);
        resetTimer();



        // sub sy pehly ye functionality add krni hy k jb b seekbar ki progrogress user change kry
        // to egg pr timerTV change ho. aur jitny second select kr k user start kry vo second ki value
        // ak variable main store ho jaey.....

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                    seconds = progress * 10;  // Progress bar ka 1 point 10 seconds k brabr ho ga.
                    changeTimerTVValue(seconds);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // ab hum ye krain gy k jb b start button press ho to stop btn aaaa jaey and vice versa
        startStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startStopBtn.getTag().toString().equals("start"))
                {// if start button pressed
                    Log.i("1234","START btn pressed");
                    // --> ab hum ye krain gy k jb b start button press ho to timer countdown shuru kr dy
                    // --> aur seekbar invisible ho jaey
                    // --> aur jb timer finish ho jaey to ak sound play ho

                    startCountdownTimer(seconds);
                    countDownTimer.start();
                    seekBar.setVisibility(View.INVISIBLE);

                    startStopBtn.setText("STOP");
                    startStopBtn.setTag("stop");
                }
                else // if stop button pressed
                {
                    Log.i("1234","STOP btn pressed");
                    //--> ab hum set krain gy k jb b stop btn press ho to
                    //      -> countdown stop ho jaey
                    //      -> seekbar visible ho jaey aur apni default position pr aajaey
                    //      -> seconds apni default value 30 pr aa jaey
                    //      -> timerTV main default timer value show ho
                    //      -> aur agr music play ho raha ho to vo stop ho jaey

                    countDownTimer.cancel();
                    seekBar.setVisibility(View.VISIBLE);
                    resetTimer();


                    startStopBtn.setText("START");
                    startStopBtn.setTag("start");
                }
            }
        });


    }











    public void changeTimerTVValue(int seconds)
    {
        int min =  seconds/60;
        int sec = seconds % 60;

        if(min<10)
        {
            if(sec<10)
            {
                timerTV.setText("0"+min+":0"+sec);
            }
            else
            {
                timerTV.setText("0"+min+":"+sec);
            }

        }
        else // if min > 10
        {
            if(sec<10)
            {
                timerTV.setText(""+min+":0"+sec);
            }
            else
            {
                timerTV.setText(""+min+":"+sec);
            }
        }

    }


    public void resetTimer()
    {
        seconds = 30;
        changeTimerTVValue(seconds);
        seekBar.setProgress(seconds/10);
    }

    public void startCountdownTimer(final int seconds)
    {

        // funciton parameter receive milliseconds so we should conver seconds into milliseconds
        countDownTimer = new CountDownTimer(seconds*1000,1000) {
            @Override
            public void onTick(long milliSecondsLeft) {
                changeTimerTVValue((int) (milliSecondsLeft/1000)); // we must convert milliseconds into seconds;
            }

            @Override
            public void onFinish() {
                timerTV.setText("Finished!");
                myMediaPlayer.start();
            }
        };
    }






}

