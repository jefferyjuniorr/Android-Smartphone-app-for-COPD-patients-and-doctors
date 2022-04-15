package com.example.copd_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Traincoachsettingvar extends AppCompatActivity {
    private Intent intent;

    EditText lastspeedshow, lasttimeshow, laststepnumbershow, Speed, Time, Speed2, Time2, Speed3, Time3, stepsizeofvar;
    Button storesetting;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcsetvar);

        ImageView backimage = findViewById(R.id.backset);
        backimage.setAlpha(0.1f);

        Speed = (EditText)findViewById(R.id.editspeed1);
        Time = (EditText)findViewById(R.id.edittime1);
        Speed2 = (EditText)findViewById(R.id.editspeed2);
        Time2 = (EditText)findViewById(R.id.edittime2);
        Speed3 = (EditText)findViewById(R.id.editTspeed3);
        Time3 = (EditText)findViewById(R.id.edittime3);
        lastspeedshow = (EditText)findViewById(R.id.editspeedlast);
        lasttimeshow = (EditText)findViewById(R.id.edittimelast);
        laststepnumbershow = (EditText)findViewById(R.id.editstepnumlast);
        stepsizeofvar = (EditText)findViewById(R.id.editTextTextPersonName7);
        storesetting = (Button)findViewById(R.id.store);

        pref = getSharedPreferences("TrainCoachSetting", MODE_PRIVATE);

        storesetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Speed" , Speed.getText().toString());
                editor.putString("Time" , Time.getText().toString());
                editor.putString("Speed2" , Speed2.getText().toString());
                editor.putString("Time2" , Time2.getText().toString());
                editor.putString("Speed3" , Speed3.getText().toString());
                editor.putString("Time3" , Time3.getText().toString());
                editor.commit();
            }
        });

        String speeddata = pref.getString("Speed", "");
        String timedata = pref.getString("Time", "");
        String stepnumberdata = pref.getString("Stepnumber", "");

        //顯示上次資料
        lastspeedshow.setText(speeddata);
        lasttimeshow.setText(timedata);
        laststepnumbershow.setText(stepnumberdata);

        Speed.setText(pref.getString("Speed", "10"));
        Time.setText(pref.getString("Time", "30"));
        Speed2.setText(pref.getString("Speed2", "10"));
        Time2.setText(pref.getString("Time2", "30"));
        Speed3.setText(pref.getString("Speed3", "10"));
        Time3.setText(pref.getString("Time3", "30"));
        stepsizeofvar.setText("15");

    }

    public void Mode(View view){
        intent = new Intent(this, Traincoachvarmode.class);
        startActivity(intent);
    }

    public void completesetting(View view){
        intent = new Intent(this, Traincoach3.class);
        startActivity(intent);
    }
}
