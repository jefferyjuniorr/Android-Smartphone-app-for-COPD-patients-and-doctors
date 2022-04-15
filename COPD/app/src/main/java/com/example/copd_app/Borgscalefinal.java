package com.example.copd_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Borgscalefinal extends AppCompatActivity {
    private Intent intent;
    private RadioGroup inputpreborg;
    private RadioButton inputpreborg1;
    private String inputpreborg2, inputpostborgfinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borgscale);

        inputpreborg = (RadioGroup)findViewById(R.id.Borgscalegroup);
    }

    private void whichborg(){
        if (inputpreborg.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
            Toast.makeText(getApplicationContext(), "請選擇！", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // one of the radio buttons is checked
            inputpreborg1 = (RadioButton)findViewById(inputpreborg.getCheckedRadioButtonId());
            inputpreborg2 = inputpreborg1.getText().toString();
            Integer getint = inputpreborg2.indexOf(" ");
            inputpostborgfinal = inputpreborg2.substring(0,getint);
            Log.d("postborg", inputpostborgfinal);

            final SharedPreferences prefborg = this.getSharedPreferences("Borgscale",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefborg.edit();
            editor.putString("postborg", inputpostborgfinal);
            editor.commit();

            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void sendborg(View view){
        Date date = new Date();
        long timeMilli = date.getTime();
        Log.d("time", String.valueOf(timeMilli));
        final SharedPreferences prefposttime = this.getSharedPreferences("Borgscalefinal",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefposttime.edit();
        editor.putString("posttime", String.valueOf(timeMilli));
        editor.commit();
        whichborg();
    }
}
