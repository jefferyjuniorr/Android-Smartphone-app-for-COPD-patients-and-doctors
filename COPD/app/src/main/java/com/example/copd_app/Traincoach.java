package com.example.copd_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.Chronometer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Traincoach extends AppCompatActivity {
    private Intent intent;
    private Button bPlay;
    private SoundPool spool;
    private int sourceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traincoach);

        bPlay = (Button) findViewById(R.id.start);

        spool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        sourceid = spool.load(this, R.raw.ring, 1);

        bPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                playSud(0);
            }
        });

    }

    public void playSud(int repeatTime) {
        AudioManager am = (AudioManager) getApplicationContext()
                .getSystemService(Context.AUDIO_SERVICE);
        // 獲取最大音量

        float audMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 左右聲道值範圍為 0.0 - 1.0
        float volRatio = audMaxVolumn;

        // 播放音頻，左右音量，設置優先級，重撥次數，速率(速率最低0.5，最高為2，1代表正常速度)
        spool.play(sourceid, volRatio, volRatio, 1, repeatTime, 1);
    }


    public void completeofstep(View view){
        intent = new Intent(this, Traincoachmenu.class);
        startActivity(intent);
    }


}
