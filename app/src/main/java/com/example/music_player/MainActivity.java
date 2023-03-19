package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
   //Widgets
    private SeekBar seekBar;
    private TextView time;
    private TextView songTitle;
    private Button play,pause,back,fast;

    //media player
    private MediaPlayer mediaPlayer;


    //Handlers
    private Handler handler=new Handler();

    //Variables

    double startTime=0;
    double finalTime=0;
    int forwardTime=10000;
    int backwardTime=10000;
    static int oneTimeOnly=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring Widgets
        seekBar=(SeekBar) findViewById(R.id.seekBar);
        time=(TextView) findViewById(R.id.time);
        songTitle=(TextView)findViewById(R.id.songTitle);
        play=(Button)findViewById(R.id.play);
        pause=(Button) findViewById(R.id.pause);
        back=(Button) findViewById(R.id.back);
        fast=(Button) findViewById(R.id.fast);



        //define the media player
        mediaPlayer=MediaPlayer.create(this,R.raw.anotherlove);
        songTitle.setText("anotherLove.mp3");


        seekBar.setClickable(false);


        // add functionalities to the buttons

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic();
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp=(int) startTime;
                if((temp+forwardTime)<=finalTime){
                    startTime=startTime+forwardTime;
                    mediaPlayer.seekTo((int)startTime);

                }else {
                    Toast.makeText(MainActivity.this,"Can't Jump Forward!",Toast.LENGTH_SHORT).show();
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp=(int) startTime;
                if((temp-backwardTime)>0){
                    startTime=startTime-backwardTime;
                    mediaPlayer.seekTo((int) startTime);

                }else{
                    Toast.makeText(MainActivity.this,"Can't Go Back!",Toast.LENGTH_SHORT).show();

                }
            }
        });







    }

    private void playMusic() {
        finalTime=mediaPlayer.getDuration();
        startTime=mediaPlayer.getCurrentPosition();

        if(oneTimeOnly==0){
            seekBar.setMax((int)finalTime);
            oneTimeOnly=1;
        }

        seekBar.setProgress((int) startTime);
        handler.postDelayed(UpdateSongTime,100);
        mediaPlayer.start();
    }


    // Creating the Runnable
    private Runnable UpdateSongTime=new Runnable() {
        @Override
        public void run() {
            startTime=mediaPlayer.getCurrentPosition();
            int minutes = (int) (startTime / 1000 / 60);
            int seconds = (int) ((startTime / 1000) % 60);
            String formattedTime = String.format("%02d:%02d", minutes, seconds);
            time.setText(formattedTime);
            seekBar.setProgress((int)startTime);
            handler.postDelayed(this,100);
        }

    };




}