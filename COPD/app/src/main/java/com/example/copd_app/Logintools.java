package com.example.copd_app;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Logintools {
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    MediaType mediaType = MediaType.parse("application/json");
    public String login(String json) throws IOException {
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("https://copd-smart-coach.ml/v1/auth/")
                .method("POST", body)
                .addHeader("X-API-KEY", "ca346174031c0cd2a6d6ccddc57b12171fe3a79669c25281163b534fca5acc5247975553aaa3443e3398286b2a61ecd779653f90ff2b651388fbe14b8eaf658cbfc9941c324164ffd09780dd50d144d5fc3ab200bd660ce7837ee9b9baefece2ce8b1fd373f3173df1777375d48c3ae9406e17b0baea088e07018a41363579f6")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }
//
//    public String bolwingJson(String username, String password) {
//        return "{'username':"  username   ","   "'password':"   password   "}";
////   "{'username':"   username   "," "'password':" password "}";
//    }

}
