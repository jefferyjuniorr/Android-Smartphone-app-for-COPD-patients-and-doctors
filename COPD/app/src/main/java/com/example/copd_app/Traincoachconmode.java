package com.example.copd_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Traincoachconmode extends AppCompatActivity {
    private Intent intent;
    private String docspeed, doctime, borguuid, token, today, userid;
    JSONObject jsondoctor, jsondoctor2;
    JSONArray jsondoctor1;
    SharedPreferences prefdoc, prefborguuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traincoachconmode);

        Calendar mCal = Calendar.getInstance();
        String dateformat = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        today = df.format(mCal.getTime());
        Log.d("today", "today is " + today);

        prefborguuid = this.getSharedPreferences("Borguuid",MODE_PRIVATE);
        borguuid = prefborguuid.getString("borguuid", "");

        final SharedPreferences prefregister = this.getSharedPreferences("Register",MODE_PRIVATE);
        token = prefregister.getString("token", "");

        final SharedPreferences prefuserid = this.getSharedPreferences("Useridstore",MODE_PRIVATE);
        userid = prefuserid.getString("userid", "");
    }

    private JSONObject getdoctor() throws JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://copd-smart-coach.ml/v1/user/"+userid+"/coach?timestamp="+today+"&borg_uuid="+borguuid+"")
                        .method("GET", null)
                        .addHeader("x-api-key", "ca346174031c0cd2a6d6ccddc57b12171fe3a79669c25281163b534fca5acc5247975553aaa3443e3398286b2a61ecd779653f90ff2b651388fbe14b8eaf658cbfc9941c324164ffd09780dd50d144d5fc3ab200bd660ce7837ee9b9baefece2ce8b1fd373f3173df1777375d48c3ae9406e17b0baea088e07018a41363579f6")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer "+token+"")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responsedoctor = response.body().string();
                    jsondoctor = new JSONObject(responsedoctor);
                    jsondoctor1 = jsondoctor.getJSONObject("data").getJSONArray("data");
                    docspeed = jsondoctor1.getJSONObject(0).getString("speed");
                    doctime = jsondoctor1.getJSONObject(0).getString("time");
                    Log.d("doctor", docspeed +"+"+ doctime);
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();

        return jsondoctor;
    }

    public void selfset (View view){
        intent = new Intent(this, Traincoachsettingcon.class);
        startActivity(intent);
    }

    public void docrecommend (View view) throws JSONException {
        getdoctor();
        if (borguuid.equals(null)){
            new AlertDialog.Builder(Traincoachconmode.this)
                    .setTitle("??????")
                    .setMessage("?????????????????????????????????????????????????????????????????????????????????????????????????????????")
                    .setPositiveButton("???", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }else{
            getdoctor();
        }
    }

    public void confirm (View view){
        intent = new Intent(this, Traincoachsettingcon.class);
        startActivity(intent);
        prefdoc = this.getSharedPreferences("doctorrecom",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefdoc.edit();
        editor.putString("Speed" , docspeed);
        editor.putString("Time" , doctime);
        editor.commit();
    }
}
