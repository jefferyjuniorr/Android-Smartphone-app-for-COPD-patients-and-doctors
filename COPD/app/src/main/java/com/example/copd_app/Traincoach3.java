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
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Traincoach3 extends AppCompatActivity {
    private Intent intent;
    private SoundPool spool;
    private int sourceid;
    //timer設定
    private CountDownTimer timer;

    private static long START_TIME_IN_MILLIS;
    private boolean timerRunning;
    private long timerleftinmillis = START_TIME_IN_MILLIS;
    private int timevar, speevar, timevar2, timevar3, speevar2, speevar3;
    private String docspeed, doctime, docspeed2, doctime2, docspeed3, doctime3;
    private final float size = (float) 60.0;
    private float transspeed, transspeed1, transspeed2, transspeed3;
    SharedPreferences pref, prefdoc;
    private int count;

    Handler handler, handler1, handler2;

    Runnable runnable;
    ArrayList<Integer> timelist = new ArrayList();
    ArrayList<Float> speedlist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState){

        pref = this.getSharedPreferences("TrainCoachSetting",MODE_PRIVATE);
        prefdoc = this.getSharedPreferences("doctorrecom",MODE_PRIVATE);

        docspeed = prefdoc.getString("Speed", "30");
        doctime = prefdoc.getString("Time", "10");
        docspeed2 = prefdoc.getString("Speed2", "30");
        doctime2 = prefdoc.getString("Time2", "10");
        docspeed3 = prefdoc.getString("Speed3", "30");
        doctime3 = prefdoc.getString("Time3", "10");

        timevar = Integer.parseInt(pref.getString("Time", doctime)) * 60000;
        speevar = Integer.parseInt(pref.getString("Speed", docspeed));
        transspeed1 = 9*size / (250*speevar);

        timevar2 = Integer.parseInt(pref.getString("Time2",  doctime2)) * 60000;
        speevar2 = Integer.parseInt(pref.getString("Speed2", docspeed2));
        transspeed2 = 9*size / (250*speevar2);
        timevar3 = Integer.parseInt(pref.getString("Time3", doctime3)) * 60000;
        speevar3 = Integer.parseInt(pref.getString("Speed3", docspeed3));
        transspeed3 = 9*size / (250*speevar3);

        timelist.add(timevar);
        timelist.add(timevar2);
        timelist.add(timevar3);
        speedlist.add(transspeed1);
        speedlist.add(transspeed2);
        speedlist.add(transspeed3);
        transspeed = speedlist.get(0);
        START_TIME_IN_MILLIS = (long) timelist.get(0);

        count = 1;

        Log.d("time", String.valueOf(timelist.get(0)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traincoach3);

        //Get reference of the XML layout's widgets
        final Button btnAlphacode = (Button)findViewById(R.id.btnAlphacode);
        final ImageButton btnstart = (ImageButton)findViewById(R.id.imageButton3);
        final ImageButton btnstop = (ImageButton)findViewById(R.id.imageButton4);
        final ImageButton btnrestart = (ImageButton)findViewById(R.id.restart);
        final Button timertext = (Button)findViewById(R.id.butimer);


        //音樂設定
        spool = new SoundPool(21, AudioManager.STREAM_MUSIC, 5);
        sourceid = spool.load(this, R.raw.knob, 1);
//
        final TextView stagename = (TextView)findViewById(R.id.timerange);

        //開始按鈕
        btnstart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                starttimer();
                anima();
                stagename.setText("階段一");
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
                resettimer();
                stagename.setText("階段一");
                count = 1;
                //彈跳視窗
                new AlertDialog.Builder(Traincoach3.this)
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

        updatecountdowntext();


    }

    //timer start
    private void starttimer(){
        final TextView stagename = (TextView)findViewById(R.id.timerange);
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
                count ++;
                Log.d("I'm here", "Finish!");
                timerRunning = false;
                handler.removeCallbacksAndMessages(null);
                btnAlphacode.clearAnimation();
                if (count == 2){
                    START_TIME_IN_MILLIS = (long) timelist.get(1);
                    timerleftinmillis = START_TIME_IN_MILLIS;
                    transspeed = speedlist.get(1);
                    updatecountdowntext();
                    starttimer();
                    anima();
                    stagename.setText("階段二");
                }
                else if(count == 3){
                    START_TIME_IN_MILLIS = (long) timelist.get(2);
                    timerleftinmillis = START_TIME_IN_MILLIS;
                    transspeed = speedlist.get(2);
                    updatecountdowntext();
                    starttimer();
                    anima();
                    stagename.setText("階段三");
                }
                else if(count == 4){
                    new AlertDialog.Builder(Traincoach3.this)
                            .setTitle("暫停")
                            .setMessage("測驗已終止，請重新開始")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else{
                    count =1;
                }
            }
        }.start();

        timerRunning = true;
        updatecountdowntext();
        Log.d("hihi", "yayayya");
    }

    //timer pause
    private void pausetimer(){
        timer.cancel();
        timerRunning = false;
        handler.removeCallbacks(runnable);
    }
    //timer reset
    private void resettimer(){
        START_TIME_IN_MILLIS = (long) timelist.get(0);
        timerleftinmillis = START_TIME_IN_MILLIS;
        transspeed = speedlist.get(0);
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

    public void anima(){
        final Button btnAlphacode = (Button)findViewById(R.id.btnAlphacode);
        int repeatcount = (int) ((timelist.get(0) / 1000)/transspeed);
        float duration = (float) transspeed*1000;
//
        //動畫設定
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F,  // 動畫開始時的透明度，0為透明
                1.0F); // 動畫結束時的透明度，1為不透明
        alphaAnimation.setDuration((long) duration); // 從開始到結束要持續的時間，單位為毫秒
        alphaAnimation.setRepeatCount(repeatcount); // 設定重複次數 -1為無限次數 0
        btnAlphacode.startAnimation(alphaAnimation);
        Log.d("anima", "Animation!");
    }
    //設定鍵
    public void tcsetting(View view){
        intent = new Intent(this, Traincoachsettingvar.class);
        startActivity(intent);
    }


}
