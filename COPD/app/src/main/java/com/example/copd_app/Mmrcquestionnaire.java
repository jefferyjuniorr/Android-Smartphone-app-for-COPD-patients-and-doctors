package com.example.copd_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Mmrcquestionnaire extends AppCompatActivity {
    private Intent intent;
    private RadioGroup inputmmrc;
    private RadioButton inputmmrcf;
    private String mmrcf;
    SharedPreferences prefmmrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmrcquestionnaire);

        inputmmrc = (RadioGroup)findViewById(R.id.radioGroup2);

    }

    public void sendmmrc(View view){
        intent = new Intent(this, QuestionnarieActivity.class);
        startActivity(intent);
        inputmmrcf = (RadioButton)findViewById(inputmmrc.getCheckedRadioButtonId());
        mmrcf = inputmmrcf.getText().toString();
        prefmmrc = this.getSharedPreferences("MMRC",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefmmrc.edit();
        editor.putString("MMRC" , String.valueOf(mmrcf));
        editor.commit();
    }
}
