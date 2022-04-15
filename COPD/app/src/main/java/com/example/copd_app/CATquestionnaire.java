package com.example.copd_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class CATquestionnaire extends AppCompatActivity {
    private Intent intent;
    private TextView tvvalue1;
    private TextView tvvalue2;
    private TextView tvvalue3;
    private TextView tvvalue4;
    private TextView tvvalue5;
    private TextView tvvalue6;
    private TextView tvvalue7;
    private TextView tvvalue8;
    private TextView textView;
    private int value1;
    private int value2;
    private int value3;
    private int value4;
    private int value5;
    private int value6;
    private int value7;
    private int value8;
    private int result;
    SharedPreferences prefcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView seekBarValue = (TextView)findViewById(R.id.catq1);

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
        final TextView seekBarValue2 = (TextView)findViewById(R.id.catq2);

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
        final TextView seekBarValue3 = (TextView)findViewById(R.id.catq3);

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
        final TextView seekBarValue4 = (TextView)findViewById(R.id.catq4);

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
        final TextView seekBarValue5 = (TextView)findViewById(R.id.catq5);

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

        SeekBar seekBar6 = (SeekBar)findViewById(R.id.seekBar6);
        final TextView seekBarValue6 = (TextView)findViewById(R.id.catq6);

        seekBar6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar6, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue6.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar6) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar6) {
                // TODO Auto-generated method stub
            }
        });

        SeekBar seekBar7 = (SeekBar)findViewById(R.id.seekBar7);
        final TextView seekBarValue7 = (TextView)findViewById(R.id.catq7);

        seekBar7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar7, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue7.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar7) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar7) {
                // TODO Auto-generated method stub
            }
        });

        SeekBar seekBar8 = (SeekBar)findViewById(R.id.seekBar8);
        final TextView seekBarValue8 = (TextView)findViewById(R.id.catq8);

        seekBar8.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar8, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue8.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar8) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar8) {
                // TODO Auto-generated method stub
            }
        });


        textView = (TextView)findViewById(R.id.catcalculate);
        tvvalue1 = (TextView)findViewById(R.id.catq1);
        tvvalue2 = (TextView)findViewById(R.id.catq2);
        tvvalue3 = (TextView)findViewById(R.id.catq3);
        tvvalue4 = (TextView)findViewById(R.id.catq4);
        tvvalue5 = (TextView)findViewById(R.id.catq5);
        tvvalue6 = (TextView)findViewById(R.id.catq6);
        tvvalue7 = (TextView)findViewById(R.id.catq7);
        tvvalue8 = (TextView)findViewById(R.id.catq8);

        findViewById(R.id.textView14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value1 = Integer.parseInt(tvvalue1.getText().toString()) ;
                value2 = Integer.parseInt(tvvalue2.getText().toString());
                value3 = Integer.parseInt(tvvalue3.getText().toString());
                value4 = Integer.parseInt(tvvalue4.getText().toString());
                value5 = Integer.parseInt(tvvalue5.getText().toString());
                value6 = Integer.parseInt(tvvalue6.getText().toString());
                value7 = Integer.parseInt(tvvalue7.getText().toString());
                value8 = Integer.parseInt(tvvalue8.getText().toString());
                result =value1+value2+value3+value4+value5+value6+value7+value8;
                textView.setText(""+result+"");
            }
        });

    }

    public void Sendcat(View view){
        intent = new Intent(this, QuestionnarieActivity.class);
        startActivity(intent);
        prefcat = this.getSharedPreferences("CAT",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefcat.edit();
        editor.putString("CAT1" , String.valueOf(value1));
        editor.putString("CAT2" , String.valueOf(value2));
        editor.putString("CAT3" , String.valueOf(value3));
        editor.putString("CAT4" , String.valueOf(value4));
        editor.putString("CAT5" , String.valueOf(value5));
        editor.putString("CAT6" , String.valueOf(value6));
        editor.putString("CAT7" , String.valueOf(value7));
        editor.putString("CAT8" , String.valueOf(value8));
        editor.putString("CATS" , String.valueOf(result));
        editor.commit();
    }
}

