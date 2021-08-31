package com.wu.housebooking.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.R;

import org.jetbrains.annotations.NotNull;

public class ProfileEditActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    TextInputEditText edtFullName;
    TextInputEditText edtEmail;
    TextInputEditText edtMobile, edtAddress;
    TextView tv_save;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_profile));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tv_save = findViewById(R.id.tv_save);
        edtFullName = findViewById(R.id.editText_name_edit_profile);
        edtEmail = findViewById(R.id.editText_email_edit_profile);
        edtMobile = findViewById(R.id.editText_phone_edit_profile);
        edtAddress = findViewById(R.id.editText_add_edit_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                edtFullName.setText(String.valueOf(snapshot.child("strFullname").getValue()));
                edtEmail.setText(String.valueOf(snapshot.child("strEmail").getValue()));
                edtMobile.setText(String.valueOf(snapshot.child("strMobi").getValue()));
                edtAddress.setText(String.valueOf(snapshot.child("address").getValue()));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(edtFullName.getText().toString().trim())) &&
                    !(TextUtils.isEmpty(edtEmail.getText().toString().trim())) &&
                    !(TextUtils.isEmpty(edtMobile.getText().toString().trim())) &&
                    !(TextUtils.isEmpty(edtAddress.getText().toString().trim())) ) {
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).child("strFullname").setValue(edtFullName.getText().toString().trim());
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).child("strEmail").setValue(edtEmail.getText().toString().trim());
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).child("strMobi").setValue(edtMobile.getText().toString().trim());
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).child("address").setValue(edtAddress.getText().toString().trim());
                }

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
}