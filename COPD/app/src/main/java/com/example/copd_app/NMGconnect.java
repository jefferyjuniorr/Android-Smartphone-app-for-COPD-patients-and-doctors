package com.example.copd_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NMGconnect  extends AppCompatActivity {
    private String useridcut;
    EditText nmgaccount, nmgpassword;
    JSONObject jsonnmg, jsonuseridput;
    SharedPreferences prefregister;
    String tokenofuser, tokenofuserfinal;
    String nmg_account, nmg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmgconnect);

        nmgaccount = (EditText)findViewById(R.id.editTextnmgacc);
        nmgpassword = (EditText)findViewById(R.id.editTextnmgpw);

        prefregister = this.getSharedPreferences("Register",MODE_PRIVATE);
        tokenofuser = prefregister.getString("token", "");

    }

    public JSONObject getuserid() throws JSONException {
        nmg_account = nmgaccount.getText().toString();
        nmg_password = nmgpassword.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "email="+nmg_account+"&password="+nmg_password+"");
                Request request = new Request.Builder()
                        .url("https://ntu-med-god.ml/api/getUserIdByEmail")
                        .method("POST", body)
                        .addHeader("Cookie", "connect.sid=s%3AwhglgQ34pjzKs99VdxF6IhBs98jg9yqt.e5ld3vZo6jPZ3AS2vXiKhvhEYsTtmy2u8G8zrMtKBoE; connect.sid=s%3Ab_k7Zu6UCsxKKC0pNhgmNaeXiYCsnLkM.kWWMVrNdNRbLv4AtYSxZj9xtMMryACtlXSYEDNJBQaw")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responsenmg = response.body().string();
                    jsonnmg = new JSONObject(responsenmg);
                    Log.d("Login", String.valueOf(jsonnmg));
                    Integer comint = responsenmg.indexOf(":");
                    Integer backint = responsenmg.indexOf("}");
                    useridcut = responsenmg.substring(comint+2, backint-1);
                    Log.d("nmg", useridcut);

                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();

        return jsonnmg;
    }

    public JSONObject putuserid() throws JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"user_id\": \""+useridcut+"\"\n}");
                Request request = new Request.Builder()
                        .url("https://copd-smart-coach.ml/v1/user/")
                        .method("PUT", body)
                        .addHeader("X-API-KEY", "ca346174031c0cd2a6d6ccddc57b12171fe3a79669c25281163b534fca5acc5247975553aaa3443e3398286b2a61ecd779653f90ff2b651388fbe14b8eaf658cbfc9941c324164ffd09780dd50d144d5fc3ab200bd660ce7837ee9b9baefece2ce8b1fd373f3173df1777375d48c3ae9406e17b0baea088e07018a41363579f6")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer "+tokenofuser+"")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseuseridput= response.body().string();
                    jsonuseridput = new JSONObject(responseuseridput);
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();

        return jsonuseridput;
    }

    private void nmgput() throws JSONException {
        if(useridcut.equals("als")){
            new AlertDialog.Builder(NMGconnect.this)
                    .setTitle("注意")
                    .setMessage("此帳號密碼未於台大醫神系統註冊過，請假查是否輸入正確，或是至台大醫神系統註冊。")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else{
            putuserid();
            final SharedPreferences prefuserid = this.getSharedPreferences("Useridstore",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefuserid.edit();
            editor.putString("currenttime", useridcut);
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void nmgcomplete(View view) throws JSONException {
        getuserid();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    nmgput();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };handler.postDelayed(runnable,5000);
    }
}
