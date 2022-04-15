package com.example.copd_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Traincoachmenu extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traincoachmenu);
    }

    public void nochangespeed(View view){
        intent = new Intent(this, Traincoach2.class);
        startActivity(intent);
        new AlertDialog.Builder(Traincoachmenu.this)
                .setTitle("提醒")
                .setMessage("進入主畫面後，請先至設定頁設定基礎設定，再開始運動。")
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void changespeed(View view){
        intent = new Intent(this, Traincoach3.class);
        startActivity(intent);
        new AlertDialog.Builder(Traincoachmenu.this)
                .setTitle("提醒")
                .setMessage("進入主畫面後，請先至設定頁設定基礎設定，再開始運動。")
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
