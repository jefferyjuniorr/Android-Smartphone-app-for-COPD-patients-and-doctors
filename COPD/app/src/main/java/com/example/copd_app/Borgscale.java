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

public class Borgscale extends AppCompatActivity {
    private Intent intent;
    private RadioGroup inputpreborg;
    private RadioButton inputpreborg1;
    private String inputpreborg2, inputpreborgfinal;

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
            inputpreborgfinal = inputpreborg2.substring(0,getint);
            Log.d("preborg", inputpreborgfinal);

            final SharedPreferences prefborg = this.getSharedPreferences("Borgscale",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefborg.edit();
            editor.putString("preborg", inputpreborgfinal);
            editor.commit();

            intent = new Intent(this, Traincoachmenu.class);
            startActivity(intent);
        }
    }

    public void sendborg(View view){
        whichborg();
    }
}

