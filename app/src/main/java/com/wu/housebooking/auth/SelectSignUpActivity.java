package com.wu.housebooking.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wu.housebooking.R;

public class SelectSignUpActivity extends AppCompatActivity {
    Button us,ow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sign_up);
        us = findViewById(R.id.us);
        ow = findViewById(R.id.ow);
        Bundle bundle = new Bundle();


        us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("userType", "user");
                Intent intent = new Intent(SelectSignUpActivity.this,SignUpActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("userType", "owner");
                Intent intent = new Intent(SelectSignUpActivity.this,SignUpActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}