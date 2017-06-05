package com.example.linh.smarttrip;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Model.Hotels;
import Model.Restaurant;


public class WelcomeActivity extends ActionBarActivity {
    ImageView Title;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);
        Title= (ImageView) findViewById(R.id.iv_title);
        final Animation animation= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        CountDownTimer countDownTimer=new CountDownTimer(2500,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(count==1){
                    Title.startAnimation(animation);
                }
                ++count;
            }

            @Override
            public void onFinish() {
                CopyDataBaseFromAsset(Restaurant.TABLE);
                CopyDataBaseFromAsset(Hotels.TABLE);
                CopyDataBaseFromAsset(Model.Shop.TABLE);
                CopyDataBaseFromAsset(Model.Park.TABLE);
                CopyDataBaseFromAsset(Model.Theater.TABLE);
                Intent intent=new Intent(WelcomeActivity.this,OptionsActivity.class);
                startActivity(intent);
                finish();
            }
        };
        countDownTimer.start();
    }
    public void CopyDataBaseFromAsset(String DBName) {
        File file = this.getDatabasePath(DBName);
        if (!file.exists()) {
            try {
                InputStream inputStream = this.getAssets().open(DBName);
                File dir = new File(this.getApplicationInfo().dataDir + "/databases");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                OutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
