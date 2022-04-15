package com.example.copd_app;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Sync extends AppCompatActivity {
    JSONObject jsongethr, jsonuuid;
    JSONArray jsongetstep;
    SharedPreferences prefborguuid;
    String pretime, posttime, userid, stepcut, preborg, postborg, timecut, prebeatfinal, postbeatfinal, token, buuidcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact);

        final SharedPreferences prefpretime = this.getSharedPreferences("TrainCoach2",MODE_PRIVATE);
        pretime = prefpretime.getString("pretime", "");

        final SharedPreferences prefposttime = this.getSharedPreferences("Borgscalefinal",MODE_PRIVATE);
        posttime = prefposttime.getString("posttime", "");

        final SharedPreferences prefuserid = this.getSharedPreferences("Useridstore",MODE_PRIVATE);
        userid = prefuserid.getString("userid","");

        final SharedPreferences prefborg = this.getSharedPreferences("Borgscale",MODE_PRIVATE);
        preborg = prefborg.getString("preborg", "");
        postborg = prefborg.getString("postborg", "");

        final SharedPreferences prefregister = this.getSharedPreferences("Register",MODE_PRIVATE);
        token = prefregister.getString("token", "");

        prefborguuid = this.getSharedPreferences("Borguuid",MODE_PRIVATE);

        getstep();
        getheartrate();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                try {
                    putinfoofborg();
                    SharedPreferences.Editor editor = prefuserid.edit();
                    editor.putString("borguuid", buuidcut);
                    editor.commit();
                    new AlertDialog.Builder(Sync.this)
                            .setTitle("成功同步")
                            .setMessage("成功同步！請點選完成離開。")
                            .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 5000);//delay 5 seconds


    }

    private void getheartrate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://ntu-med-god.ml/api/getHeartRateBySpecific?id="+userid+"&start="+pretime+"&end="+posttime+"")
                        .method("GET", null)
                        .addHeader("Cookie", "connect.sid=s%3AwhglgQ34pjzKs99VdxF6IhBs98jg9yqt.e5ld3vZo6jPZ3AS2vXiKhvhEYsTtmy2u8G8zrMtKBoE; connect.sid=s%3AfqMlESg6i92JIKrfoI-bGvluRg2zN1mE.skzmhOgyZC9rg76kAQo5kHA1af5z0vYq6WwN%2BaE4BMk")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responsehr = response.body().string();
                    Integer prebeatint1 = responsehr.indexOf("value");
                    String prebeat1 = responsehr.substring(prebeatint1+7);
                    Integer prebeatint2 = prebeat1.indexOf("}");
                    prebeatfinal = prebeat1.substring(0,prebeatint2);

                    Integer postbeatint1 = responsehr.lastIndexOf("value");
                    String postbeat1 = responsehr.substring(postbeatint1+7);
                    Integer postbeatint2 = postbeat1.indexOf("}");
                    postbeatfinal = postbeat1.substring(0,postbeatint2);
                    Log.d("beat",prebeatfinal +","+postbeatfinal);
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void getstep(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://ntu-med-god.ml/api/getStepsBySpecific?id="+userid+"&start="+pretime+"&end="+posttime+"")
                        .method("GET", null)
                        .addHeader("Cookie", "connect.sid=s%3AwhglgQ34pjzKs99VdxF6IhBs98jg9yqt.e5ld3vZo6jPZ3AS2vXiKhvhEYsTtmy2u8G8zrMtKBoE; connect.sid=s%3AU_Wju1S4dYVSDmmioe_JtHgEMeIEn0lB.yF9IexPKMlsL3AGWC743OJDtdjvPBgyuc1njB3Vk4BI")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responsestep = response.body().string();
                    Integer stepint = responsestep.indexOf("sum");
                    String stepcut1 = responsestep.substring(stepint+5);
                    Integer stepintback = stepcut1.indexOf("}");
                    stepcut = stepcut1.substring(0,stepintback);
                    Log.d("step", stepcut);
                    Integer timeint = responsestep.indexOf("time");
                    Integer timeint2 = responsestep.indexOf("Z");
                    timecut = responsestep.substring(timeint+7, timeint2+1);
                    Log.d("time", timecut);
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private JSONObject putinfoofborg() throws JSONException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"postbeat\": "+postbeatfinal+",\n  \"postborg\": "+postborg+",\n  \"prebeat\": "+prebeatfinal+",\n  \"preborg\": "+preborg+",\n  \"step\": "+stepcut+",\n  \"timestamp\": \""+timecut+"\"\n}");
                Request request = new Request.Builder()
                        .url("https://copd-smart-coach.ml/v1/user/"+userid+"/borg")
                        .method("POST", body)
                        .addHeader("X-API-KEY", "ca346174031c0cd2a6d6ccddc57b12171fe3a79669c25281163b534fca5acc5247975553aaa3443e3398286b2a61ecd779653f90ff2b651388fbe14b8eaf658cbfc9941c324164ffd09780dd50d144d5fc3ab200bd660ce7837ee9b9baefece2ce8b1fd373f3173df1777375d48c3ae9406e17b0baea088e07018a41363579f6")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer "+token+"")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseuuid = response.body().string();
                    jsonuuid = new JSONObject(responseuuid);
//                    Integer buuid = responseinput.indexOf("borg_uuid");
//                    Integer status = responseinput.indexOf("status");
                    buuidcut = jsonuuid.getJSONObject("data").getString("borg_uuid");
                    Log.d("uuid", buuidcut);


                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();

        return jsonuuid;
    }
}
