package com.example.my_web_view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
               finish();
           }
       },2000);

    }
}
