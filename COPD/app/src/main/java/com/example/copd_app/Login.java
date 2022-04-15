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
import java.text.ParseException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    private EditText username, loginpassword;
    private String responseDatalogin, statuscut, useridcut, useridcut1;
    String loginuser, loginpsw, tokencutfinal;
    JSONObject jsonlogininfo;
    SharedPreferences preacpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        username = (EditText)findViewById(R.id.editTextUsername);
        loginuser = username.getText().toString();
        loginpassword = (EditText)findViewById(R.id.editTextPassword);
        loginpsw = loginpassword.getText().toString();

        output();
    }

    public JSONObject getlogininfo() throws JSONException {
        username = (EditText)findViewById(R.id.editTextUsername);
        loginuser = username.getText().toString();
        loginpassword = (EditText)findViewById(R.id.editTextPassword);
        loginpsw = loginpassword.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \""+loginuser+"\",\n  \"password\": \""+loginpsw+"\"\n}");
                Request request = new Request.Builder()
                        .url("https://copd-smart-coach.ml/v1/auth/")
                        .method("POST", body)
                        .addHeader("X-API-KEY", "ca346174031c0cd2a6d6ccddc57b12171fe3a79669c25281163b534fca5acc5247975553aaa3443e3398286b2a61ecd779653f90ff2b651388fbe14b8eaf658cbfc9941c324164ffd09780dd50d144d5fc3ab200bd660ce7837ee9b9baefece2ce8b1fd373f3173df1777375d48c3ae9406e17b0baea088e07018a41363579f6")
                        .addHeader("Content-Type", "application/json")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseDatalogin = response.body().string();
                    jsonlogininfo = new JSONObject(responseDatalogin);
                    Log.d("Login", String.valueOf(jsonlogininfo));

                    Integer statusint = responseDatalogin.indexOf("status");
                    statuscut = responseDatalogin.substring(statusint+10, statusint+11);
                    Log.d("get", statuscut);

                    useridcut1 = jsonlogininfo.getJSONObject("data").getString("user_id");
                    Log.d("getid", useridcut1);

                    tokencutfinal = jsonlogininfo.getJSONObject("data").getJSONObject("session").getString("token");
                    Log.d("token", tokencutfinal);


                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();

        return jsonlogininfo;
    }

    private void home(){
        if(statuscut.equals("S")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            final SharedPreferences prefuserid = this.getSharedPreferences("Useridstore",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefuserid.edit();
            editor.putString("userid", useridcut1);
            editor.commit();

            final SharedPreferences prefregister = this.getSharedPreferences("Register",MODE_PRIVATE);
            editor = prefregister.edit();
            editor.putString("token", tokencutfinal);
            editor.commit();

            input();

        }
        else if(statuscut.equals("U")){
            new AlertDialog.Builder(Login.this)
                    .setTitle("注意")
                    .setMessage("請檢查是否填寫正確電子郵件與密碼，如尚未註冊請按註冊鍵進行註冊")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else{
            new AlertDialog.Builder(Login.this)
                    .setTitle("注意")
                    .setMessage("請檢查是否填寫正確電子郵件與密碼，如尚未註冊請按註冊鍵進行註冊")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

    private void input(){
        preacpw = getSharedPreferences("ACPW",MODE_PRIVATE);
        SharedPreferences.Editor editor = preacpw.edit();
        editor.putString("account", loginuser);
        editor.putString("password", loginpsw);
        editor.commit();

    }

    private void output(){
        preacpw = getSharedPreferences("ACPW",MODE_PRIVATE);

        String account = preacpw.getString("account", null);
        String password = preacpw.getString("password", null);

        username.setText(account);
        loginpassword.setText(password);
    }


    public void login(View view) throws JSONException, ParseException {
        getlogininfo();
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(Login.this);
        View v = getLayoutInflater().inflate(R.layout.loginprogressbar,null);
        alertDialog.setView(v);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                home();
                dialog.dismiss();
            }
        };handler.postDelayed(runnable,7000);
    }

    public void signup(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}
