package com.example.copd_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Traincoachsettingcon extends AppCompatActivity {

    private Intent intent;
    EditText Speed, Time, Stepnumber;
    TextView lastspeedshow, lasttimeshow, laststepnumbershow, lasttimedate;
    Button subsetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcsetcon);

        ImageView backimage = findViewById(R.id.backset);
        backimage.setAlpha(0.1f);

        //定義textviews
        Speed = (EditText)findViewById(R.id.eTspeed);
        Time = (EditText)findViewById(R.id.eTtime);
        Stepnumber = (EditText)findViewById(R.id.eTstepnumber);
        subsetting = (Button)findViewById(R.id.submitsetting);
        lastspeedshow = (TextView)findViewById(R.id.lastspeedshow);
        lasttimeshow = (TextView)findViewById(R.id.lasttimeshow);
        laststepnumbershow = (TextView)findViewById(R.id.laststepnumbershow);
        lasttimedate = (TextView)findViewById(R.id.lasttimedate);

        //取今日時間
        Calendar mCal = Calendar.getInstance();
        final CharSequence today = DateFormat.format("yyyy-MM-dd kk:mm:ss", mCal.getTime());

        //lasttimedate.setText(today);

        //建構sharedpreference
        final SharedPreferences pref = this.getSharedPreferences("TrainCoachSetting",MODE_PRIVATE);

        //儲存Onclick
        subsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("currenttime", (String) today);
                editor.putString("Speed" , Speed.getText().toString());
                editor.putString("Time" , Time.getText().toString());
                editor.putString("Stepnumber" , Stepnumber.getText().toString());
                editor.commit();
            }
        });

        //取sharedpreference
        String speeddata = pref.getString("Speed", "");
        String timedata = pref.getString("Time", "");
        String stepnumberdata = pref.getString("Stepnumber", "");

        //顯示上次資料
        lastspeedshow.setText(speeddata);
        lasttimeshow.setText(timedata);
        laststepnumbershow.setText(stepnumberdata);

        //直接填入之前儲存資料
        Speed.setText(speeddata);
        Time.setText(timedata);
        Stepnumber.setText(stepnumberdata);


//        //建立意圖物件
//        Intent intent = new Intent(Traincoachsettingcon.this, Traincoach2.class);
//
//        //啟用意圖
//        startActivity(intent);
//        Intent intentch = new Intent();
//        intent.setClass(Traincoachsettingcon.this, Traincoach2.class);
//
//        Bundle bundlesetcon = new Bundle();
//        double speed = Double.parseDouble(Speed.getText().toString());
//        double time = Double.parseDouble(Time.getText().toString());
//        bundlesetcon.putDouble("speed", speed);
//        bundlesetcon.putDouble("time", time);
//
//        intentch.putExtras(bundlesetcon);


    }

    public void Mode(View view){
        intent = new Intent(this, Traincoachconmode.class);
        startActivity(intent);
    }

    public void completesetting(View view){
        intent = new Intent(this, Traincoach2.class);
        startActivity(intent);
    }

}
