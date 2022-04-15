package com.example.copd_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class Register extends AppCompatActivity {
    private EditText inputlastname, inputname, inputage, inputemail, inputbirth, inputheight, inputweight, inputphonenumber, inputid, inputpw1,inputpw2;
    private RadioGroup inputgender;
    private RadioButton inputgender1;
    private String sinputln;
    private String sinputfn;
    private String sinputageori;
    private String sinputemail;
    private String sinputbirth;
    private String sinputhori;
    private String sinputwori;
    private String sinputphone;
    private String sinputid;
    private String sinputpw1;
    private String sinputpw2;
    private String sinputgender;
    private String statuscut;
    private String cut2;
    private int sinputage, sinputh, sinputw;
    JSONObject jsonnewlogininfo;
    SharedPreferences prefregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputlastname = (EditText)findViewById(R.id.editTextlastname);
        sinputln = inputlastname.getText().toString();

        inputname = (EditText)findViewById(R.id.editTextname);
        inputage = (EditText)findViewById(R.id.editTextage);
        inputemail = (EditText)findViewById(R.id.editTextemail);
        inputbirth = (EditText)findViewById(R.id.editTextbirth);
        inputheight = (EditText)findViewById(R.id.editTextheight);
        inputweight = (EditText)findViewById(R.id.editTextweight);
        inputphonenumber = (EditText)findViewById(R.id.editTextphonenumber);
        inputid = (EditText)findViewById(R.id.editTextidcard);
        inputpw1 = (EditText)findViewById(R.id.editTextpassword1);
        inputpw2 = (EditText)findViewById(R.id.editTextpassword2);
        inputgender = (RadioGroup)findViewById(R.id.radioGroup);

        prefregister = this.getSharedPreferences("Register",MODE_PRIVATE);
    }

    public JSONObject inputlogininfonoid() throws JSONException {
        sinputln = inputlastname.getText().toString();
        sinputfn = inputname.getText().toString();
        sinputageori = inputage.getText().toString();
        sinputage = Integer.parseInt(sinputageori);
        sinputemail = inputemail.getText().toString();
        sinputbirth = inputbirth.getText().toString();
        sinputhori = inputheight.getText().toString();
        sinputh = Integer.parseInt(sinputhori);
        sinputwori = inputweight.getText().toString();
        sinputw = Integer.parseInt(sinputwori);
        sinputphone = inputphonenumber.getText().toString();
        sinputid = inputid.getText().toString();
        sinputpw1 = inputpw1.getText().toString();
        sinputpw2 = inputpw2.getText().toString();
        inputgender1 = (RadioButton)findViewById(inputgender.getCheckedRadioButtonId());
        sinputgender = inputgender1.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"age\": "+sinputage+",\n  \"birthday\": \""+sinputbirth+"\",\n  \"email\": \""+sinputemail+"\",\n  \"firstname\": \""+sinputfn+"\",\n  \"gender\": \""+sinputgender+"\",\n  \"height\": "+sinputh+",\n  \"identity\": \""+sinputid+"\",\n  \"lastname\": \""+sinputln+"\",\n  \"password\": \""+sinputpw1+"\",\n  \"phone\": \""+sinputphone+"\",\n  \"weight\": "+sinputw+"\n}");
                Request request = new Request.Builder()
                        .url("https://copd-smart-coach.ml/v1/auth/")
                        .method("PUT", body)
                        .addHeader("X-API-KEY", "ca346174031c0cd2a6d6ccddc57b12171fe3a79669c25281163b534fca5acc5247975553aaa3443e3398286b2a61ecd779653f90ff2b651388fbe14b8eaf658cbfc9941c324164ffd09780dd50d144d5fc3ab200bd660ce7837ee9b9baefece2ce8b1fd373f3173df1777375d48c3ae9406e17b0baea088e07018a41363579f6")
                        .addHeader("Content-Type", "application/json")
                        .build();
                try {
                    Response responseinfo = client.newCall(request).execute();
                    String responselogininfonew = responseinfo.body().string();
                    jsonnewlogininfo = new JSONObject(responselogininfonew);
                    Log.d("logininfo", String.valueOf(jsonnewlogininfo));
                    Integer statusint = responselogininfonew.indexOf("status");
                    statuscut = responselogininfonew.substring(statusint+10, statusint+11);
                    Log.d("get", statuscut);

//                    Integer tokencut1 = responselogininfonew.lastIndexOf("token");
//                    String cut1 = responselogininfonew.substring(tokencut1+8);
//                    Integer tokencut2 = cut1.indexOf("}");
                    cut2 = jsonnewlogininfo.getJSONObject("data").getJSONObject("session").getString("token");
                    Log.d("get", cut2);
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();

        return jsonnewlogininfo;
    }

    private void tonmghome(){
        if(sinputpw1.equals(sinputpw2)){
            if(statuscut.equals("S")){
                Intent intent = new Intent(this, NMGconnect.class);
                startActivity(intent);
            }
            else if(statuscut.equals("b")){
                new AlertDialog.Builder(Register.this)
                        .setTitle("注意")
                        .setMessage("此帳號或身份已註冊過，請重新填寫！")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
            else{
                new AlertDialog.Builder(Register.this)
                        .setTitle("注意")
                        .setMessage("此帳號或身份已註冊過，請重新填寫！")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        }else{
            new AlertDialog.Builder(Register.this)
                    .setTitle("注意")
                    .setMessage("兩次輸入的密碼不相同！請重新輸入")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }

    }

    private void storetoken(){
        SharedPreferences.Editor editor = prefregister.edit();
        editor.putString("token", cut2);
        editor.commit();
    }

    public void completeregister(View view) throws JSONException {
        inputlogininfonoid();

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tonmghome();
                storetoken();
            }
        };handler.postDelayed(runnable,5000);
    }
}
