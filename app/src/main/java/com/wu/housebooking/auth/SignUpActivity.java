package com.wu.housebooking.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wu.housebooking.DashboardActivity;
import com.wu.housebooking.MainActivity;
import com.wu.housebooking.R;
import com.wu.housebooking.model.SignUpModel;

import org.jetbrains.annotations.NotNull;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText edtFullName;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtMobile;
    Button btnSignUp;
    TextView txtLogin;
    String strFullname, strEmail, strPassword, strMobi, strMessage;
    String userType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        if (bundle!=null)
            userType = bundle.getString("userType");

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        edtFullName = findViewById(R.id.editText_name_register);
        edtEmail = findViewById(R.id.editText_email_register);
        edtPassword = findViewById(R.id.editText_password_register);
        edtMobile = findViewById(R.id.editText_phoneNo_register);

        btnSignUp = findViewById(R.id.button_submit);
        txtLogin = findViewById(R.id.textView_login_register);


        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                doSignUp();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
    private void doSignUp() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        strFullname = edtFullName.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strPassword = edtPassword.getText().toString().trim();
        strMobi = edtMobile.getText().toString().trim();


        String type = userType;
        if (!TextUtils.isEmpty(strFullname) && !TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword) && !TextUtils.isEmpty(strMobi)){
            
            if (strPassword.length()>5){
                auth.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String pathKey = strFullname+strMobi+strEmail;
                            SignUpModel signUpModel = new SignUpModel(strFullname,strEmail,strPassword,strMobi,type,auth.getCurrentUser().getUid());
                             mDatabase.child(auth.getCurrentUser().getUid()).setValue(signUpModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignUpActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(SignUpActivity.this, "Login Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toast.makeText(SignUpActivity.this, "Password Must be 6 character ", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(SignUpActivity.this, "Filed Can't be Empty", Toast.LENGTH_SHORT).show();
        }
    }
}