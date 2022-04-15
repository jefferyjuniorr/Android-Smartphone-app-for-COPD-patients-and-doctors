package com.example.copd_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Lifestylequestionnaire extends AppCompatActivity {
    private Intent intent;
    private EditText etscore;
    private TextView lfvalue1;
    private TextView lfvalue2;
    private TextView lfvalue3;
    private TextView lfvalue4;
    private TextView lfvalue5;
    SharedPreferences preflf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle);

        etscore = (EditText) findViewById(R.id.etscore);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView seekBarValue = (TextView)findViewById(R.id.q1ans);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        SeekBar seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        final TextView seekBarValue2 = (TextView)findViewById(R.id.q2ans);

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue2.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {
                // TODO Auto-generated method stub
            }
        });

        SeekBar seekBar3 = (SeekBar)findViewById(R.id.seekBar3);
        final TextView seekBarValue3 = (TextView)findViewById(R.id.q3ans);

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar3, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue3.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar3) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar3) {
                // TODO Auto-generated method stub
            }
        });

        SeekBar seekBar4 = (SeekBar)findViewById(R.id.seekBar4);
        final TextView seekBarValue4 = (TextView)findViewById(R.id.q4ans);

        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar4, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue4.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar4) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar4) {
                // TODO Auto-generated method stub
            }
        });

        SeekBar seekBar5 = (SeekBar)findViewById(R.id.seekBar5);
        final TextView seekBarValue5 = (TextView)findViewById(R.id.q5ans);

        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar5, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue5.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar5) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar5) {
                // TODO Auto-generated method stub
            }
        });

        lfvalue1 = (TextView)findViewById(R.id.q1ans);
        lfvalue2 = (TextView)findViewById(R.id.q2ans);
        lfvalue3 = (TextView)findViewById(R.id.q3ans);
        lfvalue4 = (TextView)findViewById(R.id.q4ans);
        lfvalue5 = (TextView)findViewById(R.id.q5ans);


    }

    public void Sendlife(View view){
        intent = new Intent(this, QuestionnarieActivity.class);
        startActivity(intent);
        preflf = this.getSharedPreferences("LF",MODE_PRIVATE);
        SharedPreferences.Editor editor = preflf.edit();
        editor.putString("LF1" , String.valueOf(lfvalue1));
        editor.putString("LF2" , String.valueOf(lfvalue2));
        editor.putString("LF3" , String.valueOf(lfvalue3));
        editor.putString("LF4" , String.valueOf(lfvalue4));
        editor.putString("LF5" , String.valueOf(lfvalue5));
        editor.commit();
    }
}
