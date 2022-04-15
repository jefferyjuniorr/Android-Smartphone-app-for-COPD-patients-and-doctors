package com.example.copd_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private Intent intent;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact);
    }

    public void Body(View view){
        intent = new Intent(this, BodydataActivity.class);
        startActivity(intent);
    }

    public void Questionnaire(View view){
        intent = new Intent(this, QuestionnarieActivity.class);
        startActivity(intent);
    }

    public void Offline(View view){
        intent = new Intent(this, OfflineActivity.class);
        startActivity(intent);
    }

    public void Online(View view){
        intent = new Intent(this, OnlineActivity.class);
        startActivity(intent);
    }

    public void train(View view){
        intent = new Intent(this, Traincoachhello.class);
        startActivity(intent);
    }

    public void sync(View view){
        intent = new Intent(this, Sync.class);
        startActivity(intent);
    }


}

