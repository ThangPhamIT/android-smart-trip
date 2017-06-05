package com.example.linh.smarttrip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.io.InputStream;

import Model.Hotels;


public class BeforeWelcomeActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_welcome);
        CountDownTimer timer =new CountDownTimer(2300,500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(BeforeWelcomeActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.start();
    }
}
