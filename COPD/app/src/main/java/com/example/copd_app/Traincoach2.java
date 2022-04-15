package com.example.copd_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Traincoach2 extends AppCompatActivity {
    private Intent intent;
    private SoundPool spool;
    private int sourceid;
    //timer設定
    private CountDownTimer timer;

    private static long START_TIME_IN_MILLIS;
    private boolean timerRunning;
    private long timerleftinmillis = START_TIME_IN_MILLIS;
    private int speed;
    private String docspeed, doctime;
    private float size = (float) 60.0;
    private float transspeed;
    SharedPreferences pref, prefdoc;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pref = this.getSharedPreferences("TrainCoachSetting",MODE_PRIVATE);
        prefdoc = this.getSharedPreferences("doctorrecom",MODE_PRIVATE);

        docspeed = prefdoc.getString("Speed", "30");
        doctime = prefdoc.getString("Time", "10");

        int timecon = Integer.parseInt(pref.getString("Time", doctime)) * 60000;
        START_TIME_IN_MILLIS = timecon;
        int speecon = (int) Double.parseDouble(pref.getString("Speed", docspeed));
        speed = speecon;
        transspeed = 9*size / (250*speed);
        Log.d("time", String.valueOf(timecon));
        Log.d("speed", String.valueOf(speecon));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traincoach2);

        //Get reference of the XML layout's widgets
        final Button btnAlphacode = (Button)findViewById(R.id.btnAlphacode);
        final ImageButton btnstart = (ImageButton)findViewById(R.id.imageButton3);
        final ImageButton btnstop = (ImageButton)findViewById(R.id.imageButton4);
        final ImageButton btnrestart = (ImageButton)findViewById(R.id.restart);
        final Button timertext = (Button)findViewById(R.id.butimer);


        //音樂設定
        spool = new SoundPool(21, AudioManager.STREAM_MUSIC, 5);
        sourceid = spool.load(this, R.raw.knob, 1);

        int repeatcount = (int) ((START_TIME_IN_MILLIS / 1000)/transspeed);
        float duration = (float) transspeed*1000;

        //動畫設定
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F,  // 動畫開始時的透明度，0為透明
                1.0F); // 動畫結束時的透明度，1為不透明
        alphaAnimation.setDuration((long) duration); // 從開始到結束要持續的時間，單位為毫秒
        alphaAnimation.setRepeatCount(repeatcount); // 設定重複次數 -1為無限次數 0
//        Log.d("animation", String.valueOf(duration));
//        Log.d("animation", String.valueOf(repeatcount));

        //開始按鈕
        btnstart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                starttimer();
                btnAlphacode.startAnimation(alphaAnimation);
                gettimestamp();
            }
        });

        //停止按鈕
        btnstop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //動畫
                btnAlphacode.clearAnimation();
                //計時器
                pausetimer();
                //彈跳視窗
                new AlertDialog.Builder(Traincoach2.this)
                        .setTitle("暫停")
                        .setMessage("測驗已終止，請重新開始")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        //重新開始按鈕
        btnrestart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //動畫
                btnAlphacode.clearAnimation();
                //計時器
                resettimer();
            }
        });

        updatecountdowntext();

    }

    //timer start
    private void starttimer(){
        final Button btnAlphacode = (Button)findViewById(R.id.btnAlphacode);
        handler = new Handler();
        runnable=new Runnable(){
            @Override
            public void run() {
                playSud(10);
                handler.postDelayed(this, (long) (transspeed*1000));
            }
        }; handler.post(runnable);
        timer = new CountDownTimer(timerleftinmillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerleftinmillis = millisUntilFinished;
                updatecountdowntext();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                handler.removeCallbacks(runnable);
                btnAlphacode.clearAnimation();
            }
        }.start();

        timerRunning = true;
    }
    //timer pause
    private void pausetimer(){
        timer.cancel();
        timerRunning = false;
        handler.removeCallbacks(runnable);
    }
    //timer reset
    private void resettimer(){
        timerleftinmillis = START_TIME_IN_MILLIS;
        updatecountdowntext();
    }
    //timer text
    private void updatecountdowntext(){
        int minutes = (int) (timerleftinmillis / 1000) / 60;
        int seconds = (int) (timerleftinmillis / 1000) % 60;

        String timerleftformatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        Button timertext = (Button)findViewById(R.id.butimer);
        timertext.setText(timerleftformatted);

    }

    private void gettimestamp(){
        Date date = new Date();
        long timeMilli = date.getTime();
        Log.d("time", String.valueOf(timeMilli));
        final SharedPreferences prefpretime = this.getSharedPreferences("TrainCoach2",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefpretime.edit();
        editor.putString("pretime", String.valueOf(timeMilli));
        editor.commit();
    }

    public void sendtrain(View view){
        intent = new Intent(this, Borgscalefinal.class);
        startActivity(intent);
    }
    //music setting
    public void playSud(int repeatTime) {
        AudioManager am = (AudioManager) getApplicationContext()
                .getSystemService(Context.AUDIO_SERVICE);
        // 獲取最大音量

        float audMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 左右聲道值範圍為 0.0 - 1.0
        float volRatio = audMaxVolumn;

        // 播放音頻，左右音量，設置優先級，重撥次數，速率(速率最低0.5，最高為2，1代表正常速度)
        spool.play(sourceid, volRatio, volRatio, 1, 0, 1);
    }


    //設定鍵
    public void tcsetting(View view){
        intent = new Intent(this, Traincoachsettingcon.class);
        startActivity(intent);
    }

}
