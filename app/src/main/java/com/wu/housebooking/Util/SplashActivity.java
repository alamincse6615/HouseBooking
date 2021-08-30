package com.wu.housebooking.Util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.wu.housebooking.DashboardActivity;
import com.wu.housebooking.R;
import com.wu.housebooking.auth.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser() != null){
                    if (firebaseAuth.getCurrentUser().getUid() != null){
                        Intent intent=new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);
    }
}