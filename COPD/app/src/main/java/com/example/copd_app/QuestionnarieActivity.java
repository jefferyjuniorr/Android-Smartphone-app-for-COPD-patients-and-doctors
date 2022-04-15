package com.example.copd_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QuestionnarieActivity extends AppCompatActivity {
    private Intent intent;
    SharedPreferences prefcat, preflf, prefmmrc;
    String cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cats, lf1, lf2, lf3, lf4, lf5, mmrcs;
    private String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        Calendar mCal = Calendar.getInstance();
        String dateformat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        today = df.format(mCal.getTime());
        Log.d("today", "today is " + today);
    }

    public void CAT(View view){
        intent = new Intent(this, CATquestionnaire.class);
        startActivity(intent);
    }

    public void Lifestyle(View view){
        intent = new Intent(this, Lifestylequestionnaire.class);
        startActivity(intent);
    }

    public void mmrc(View view){
        intent = new Intent(this, Mmrcquestionnaire.class);
        startActivity(intent);
    }

    public void cloud(View view){
        prefcat = this.getSharedPreferences("CAT",MODE_PRIVATE);
        preflf = this.getSharedPreferences("LF",MODE_PRIVATE);
        prefmmrc = this.getSharedPreferences("MMRC",MODE_PRIVATE);

        cat1 = prefcat.getString("CAT1", "0");
        cat2 = prefcat.getString("CAT2", "0");
        cat3 = prefcat.getString("CAT3", "0");
        cat4 = prefcat.getString("CAT4", "0");
        cat5 = prefcat.getString("CAT5", "0");
        cat6 = prefcat.getString("CAT6", "0");
        cat7 = prefcat.getString("CAT7", "0");
        cat8 = prefcat.getString("CAT8", "0");
        cats = prefcat.getString("CATS", "0");

        lf1 = preflf.getString("LF1", "0");
        lf2 = preflf.getString("LF2", "0");
        lf3 = preflf.getString("LF3", "0");
        lf4 = preflf.getString("LF4", "0");
        lf5 = preflf.getString("LF5", "0");

        mmrcs = prefmmrc.getString("MMRC", "0");

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"cat1\": "+cat1+",\n  \"cat2\": "+cat2+",\n  \"cat3\": "+cat3+",\n  \"cat4\": "+cat4+",\n  \"cat5\": "+cat5+",\n  \"cat6\": "+cat6+",\n  \"cat7\": "+cat7+",\n  \"cat8\": "+cat8+",\n  \"catsum\": "+cats+",\n  \"eq1\": "+lf1+",\n  \"eq2\": "+lf2+",\n  \"eq3\": "+lf3+",\n  \"eq4\": "+lf4+",\n  \"eq5\": "+lf5+",\n  \"mmrc\": "+mmrcs+",\n  \"timestamp\": \""+today+"\"\n}");
                Request request = new Request.Builder()
                        .url("https://copd-smart-coach.ml/v1/user/k87j6e7c/survey")
                        .method("POST", body)
                        .addHeader("X-API-KEY", "ca346174031c0cd2a6d6ccddc57b12171fe3a79669c25281163b534fca5acc5247975553aaa3443e3398286b2a61ecd779653f90ff2b651388fbe14b8eaf658cbfc9941c324164ffd09780dd50d144d5fc3ab200bd660ce7837ee9b9baefece2ce8b1fd373f3173df1777375d48c3ae9406e17b0baea088e07018a41363579f6")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer 820f1953e0336ee685292a505b35c4a7ba045290358f446bde8e8ae580c2d89beb6f3325f519f3bca39c0cd8fcf571ee6afb80b7f10e2654b366ce878cee2526a70a3267f8b7b01d5c1095504e22f798641028e8c3a8323faaf52b8004099739a424dec1ebd5a10dd877837568239251e36d2419852e4bceb82bc71d14b47e5d")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseDatahr = response.body().string();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }).start();

        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
