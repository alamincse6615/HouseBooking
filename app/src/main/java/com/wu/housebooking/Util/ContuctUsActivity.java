package com.wu.housebooking.Util;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wu.housebooking.DashboardActivity;
import com.wu.housebooking.PaymentActivity;
import com.wu.housebooking.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ContuctUsActivity extends AppCompatActivity {

    Toolbar toolbar;

   EditText edtFullName;

    EditText edtEmail;

    EditText edtMobile;
    EditText edtSubject;
    EditText edtDescription;
    Button btnSubmit;
    String strFullname, strEmail, strMobi, strSubject, strDescription, strMessage;
    FirebaseAuth auth;
    private DatabaseReference mDatabase;
    String currentDateandTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contuct_us);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_contact_us));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("ContactInfo");
        auth = FirebaseAuth.getInstance();
        edtFullName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtMobile = findViewById(R.id.edt_phone);
        edtSubject = findViewById(R.id.edt_subject);
        edtDescription = findViewById(R.id.edt_description);
        btnSubmit = findViewById(R.id.btn_submit);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd'_'HH:mm:ss");
        currentDateandTime = sdf.format(new Date());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(edtEmail.getText().toString().trim())) &&
                    !(TextUtils.isEmpty(edtSubject.getText().toString().trim())) &&
                    !(TextUtils.isEmpty(edtDescription.getText().toString().trim()))){

                    String path = currentDateandTime + String.valueOf(auth.getCurrentUser().getUid());
                    HashMap<String, String> contactMap = new HashMap<>();
                    contactMap.put("id",String.valueOf(path));
                    contactMap.put("uid",String.valueOf(auth.getCurrentUser().getUid()));
                    contactMap.put("name",String.valueOf(edtFullName.getText().toString().trim()));
                    contactMap.put("email",String.valueOf(edtEmail.getText().toString().trim()));
                    contactMap.put("mobile",String.valueOf(edtMobile.getText().toString().trim()));
                   // contactMap.put("mobile",null != String.valueOf(edtMobile.getText().toString().trim()) ? String.valueOf(edtMobile.getText().toString().trim()) : "");
                    contactMap.put("subject",String.valueOf(edtSubject.getText().toString().trim()));
                    contactMap.put("description",String.valueOf(edtDescription.getText().toString().trim()));
                    mDatabase.child(path).setValue(contactMap);

                    new AlertDialog.Builder(ContuctUsActivity.this)
                            .setTitle("Information submitted")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_star_filled)
                            .setMessage("Your Information has been Successfully send \n An admin will see this ..")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){
                                    Intent intent = new Intent(ContuctUsActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                    edtFullName.setText("");
                    edtEmail.setText("");
                    edtMobile.setText("");
                    edtSubject.setText("");
                    edtDescription.setText("");

                }else {
                    Toast.makeText(ContuctUsActivity.this, "Email, Subject & Description can't be empty", Toast.LENGTH_SHORT).show();
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