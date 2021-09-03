package com.wu.housebooking.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wu.housebooking.DashboardActivity;
import com.wu.housebooking.MainActivity;
import com.wu.housebooking.R;
import com.wu.housebooking.admin.AdminDashboardActivity;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    String strEmail, strPassword;
    MaterialButton loginBtn,button_skip_login_activity;
    TextView textView_signup_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button_skip_login_activity = findViewById(R.id.button_skip_login_activity);
        edtEmail = findViewById(R.id.editText_email_login_activity);
        edtPassword = findViewById(R.id.editText_password_login_activity);
        loginBtn = findViewById(R.id.button_login_activity);
        textView_signup_login = findViewById(R.id.textView_signup_login);
        textView_signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SelectSignUpActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        button_skip_login_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        
    }

    private void doLogin() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        strEmail = edtEmail.getText().toString().trim();
        strPassword = edtPassword.getText().toString().trim();

        if (strEmail.equals("admin") && strPassword.equals("admin")){
            Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
        }
        else
        if (!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword)){
            if (strPassword.length()>5){
                auth.signInWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_SHORT).show();

                    }
                });
            }else {
                Toast.makeText(this, "Password Must be 6 character", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Filed Can't be Empty", Toast.LENGTH_SHORT).show();
        }
    }
}