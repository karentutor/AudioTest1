package com.example.audiotest1;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    //This enables us to manage audio
    AudioManager audioManager;

    public void play(View view) {

        mediaPlayer.start();
    }

    public void pause(View view) {

        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.marbles);
        // SOUND SCRUBBING
        //
        final SeekBar scrubControl = (SeekBar) findViewById(R.id.scrubSeekBar);

        // Now we determine the maximum length of the long
        scrubControl.setMax(mediaPlayer.getDuration());

        scrubControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("scrub seekbar changed", Integer.toString(progress));

                //here we use the scrubseek bar to set the position of the sound based on location
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                // Seek value to be updated to current condition of the media player
                scrubControl.setProgress(mediaPlayer.getCurrentPosition());
            }
            // start right away 1 second
        }, 0,300);



        // VOLUME CONTROL
        // Every device has its own maximum volume, we need to determine this
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // Get the volume at start
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //This way pause can acceess as well as play

        SeekBar volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

        volumeControl.setMax(maxVolume);

        volumeControl.setProgress(currentVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //This is where we are going to set the volume
                // Every device has a different maximum volume
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}

