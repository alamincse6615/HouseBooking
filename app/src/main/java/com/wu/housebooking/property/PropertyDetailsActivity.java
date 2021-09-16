package com.wu.housebooking.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wu.housebooking.MainActivity;
import com.wu.housebooking.PaymentActivity;
import com.wu.housebooking.R;
import com.wu.housebooking.auth.LoginActivity;
import com.wu.housebooking.model.ItemProperty;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PropertyDetailsActivity extends AppCompatActivity {
    DatePickerDialog picker;
    String booking_date="";
    String return_date="";
    ImageView imageFloor, imageMap, imageCall;
    TextView txtName, txtAddress, txtPrice, txtBed, txtBath, txtArea, txtPhone, txtAmenities;
    WebView webView;
    Toolbar toolbar;
    ScrollView mScrollView;
    ProgressBar mProgressBar;
    ItemProperty objBean;
    String Id;
    ArrayList<String> mGallery, mAmenities;
    private FragmentManager fragmentManager;
    Menu menu;
    View view, view1;

    LinearLayout adLayout;
    boolean iswhichscreen;
    ImageView image_fur, image_very;

    LinearLayout lay_bed, lay_bath, lay_feet, lay_verify, lay_semi;
    View view_bed, view_bath, view_feet, view_verify;


    ImageView ivGallery;
    TextView textAddress;
    TextView textPhone;
    TextView textBed;
    TextView textBath;
    TextView textSquare;
    TextView text;
    TextView textPrice;
    TextView textFur;
    TextView  textVery;
    TextView  property_description;
    TextView  ContainerAmenities;
    TextView  tv_continue;
    ImageView image_floor;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,bookingRef,mDatabase;
    String propertyUid = "";
    String propertyOwnerUid = "";
    String currentDateandTime = "";
    String thisBookingId = "";
    String continueButtonTxt = "Rent";
    String propertyOwnerNumber = "";
    String userName = "";
    String userPhone = "";
    String userEmail = "";
    String userAddress = "";
    String currentActiveUserId = "";
    Boolean isMyProperty = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        Bundle bundle = getIntent().getExtras();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_property_details));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Extract the data…
        if (bundle!=null)
            propertyUid = bundle.getString("propertyUid");

        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        bookingRef = FirebaseDatabase.getInstance().getReference("Booking");
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth  = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        mDatabase.child(String.valueOf(firebaseAuth.getCurrentUser().getUid())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                userName = String.valueOf(snapshot.child("strFullname").getValue());
                userPhone = String.valueOf(snapshot.child("strMobi").getValue());
                userEmail = String.valueOf(snapshot.child("strEmail").getValue());
                userAddress = String.valueOf(snapshot.child("address").getValue());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            currentActiveUserId = firebaseAuth.getCurrentUser().getUid();
        }

        ivGallery = findViewById(R.id.ivGallery);
        textAddress = findViewById(R.id.textAddress);
        textPhone = findViewById(R.id.textPhone);
        textBed = findViewById(R.id.textBed);
        textBath = findViewById(R.id.textBath);
        textSquare = findViewById(R.id.textSquare);
        textFur = findViewById(R.id.textFur);
        text = findViewById(R.id.text);
        textPrice = findViewById(R.id.textPrice);
        textVery = findViewById(R.id.textVery);
        property_description = findViewById(R.id.property_description);
        ContainerAmenities = findViewById(R.id.ContainerAmenities);
        image_floor = findViewById(R.id.image_floor);
        tv_continue = findViewById(R.id.tv_continue);
        imageCall = findViewById(R.id.imageCall);
        databaseReference.child(propertyUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (String.valueOf(snapshot.child("gallery_image").getValue()).length()>5)
                    Picasso.get().load(String.valueOf(snapshot.child("gallery_image").getValue())).into(ivGallery);
                text.setText(String.valueOf(snapshot.child("propertyName").getValue()));
                textPrice.setText(String.valueOf(snapshot.child("propertyPrice").getValue()));
                textAddress.setText(String.valueOf(snapshot.child("propertyAddress").getValue()));
                textPhone.setText(String.valueOf(snapshot.child("propertyPhone").getValue()));
                textBed.setText(String.valueOf(snapshot.child("propertyBed").getValue()));
                textBath.setText(String.valueOf(snapshot.child("propertyBath").getValue()));
                textSquare.setText(String.valueOf(snapshot.child("propertyArea").getValue()));
                textFur.setText(String.valueOf(snapshot.child("propertyPropertyFur").getValue()));
                property_description.setText(String.valueOf(snapshot.child("propertyDescription").getValue()));
                ContainerAmenities.setText(String.valueOf(snapshot.child("propertyAmenities").getValue()));
                if (String.valueOf(snapshot.child("floor_plan_image").getValue()).length()>5)
                Picasso.get().load(String.valueOf(snapshot.child("floor_plan_image").getValue())).into(image_floor);
                //tv_continue.setText(String.valueOf(snapshot.child("gallery_image").getValue()));

                propertyOwnerNumber = String.valueOf(snapshot.child("propertyPhone").getValue());
                propertyOwnerUid = String.valueOf(snapshot.child("UId").getValue());

                if (String.valueOf(propertyOwnerUid).equals(currentActiveUserId)){
                    isMyProperty = true;
                   // tv_continue.setText("Own Property");

                    tv_continue.setBackgroundColor(getResources().getColor(R.color.white));
                    tv_continue.setTextColor(getResources().getColor(R.color.black));
                    tv_continue.setText("My Property");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        bookingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot PropertySnapshot: snapshot.getChildren()) {

                    if (String.valueOf(PropertySnapshot.child("PropertyId").getValue()).equals(propertyUid)){
                        if (String.valueOf(firebaseAuth.getCurrentUser().getUid()).equals(String.valueOf(PropertySnapshot.child("uid").getValue()))){
                            if(Boolean.parseBoolean(String.valueOf(PropertySnapshot.child("isAcceptByPropertyOwner").getValue()))){
                                thisBookingId = String.valueOf(PropertySnapshot.child("id").getValue());
                                continueButtonTxt = "PayNow";
                                tv_continue.setText("Pay Now");
                                tv_continue.setBackgroundColor(getResources().getColor(R.color.detail_circle_green));
                            }
                            else {
                                continueButtonTxt = "PendingRe Request";
                                tv_continue.setText("PendingRe Request");
                                tv_continue.setBackgroundColor(getResources().getColor(R.color.yellow));
                            }

                            if(Boolean.parseBoolean(String.valueOf(PropertySnapshot.child("isPayment").getValue()))){
                                thisBookingId = String.valueOf(PropertySnapshot.child("id").getValue());
                                continueButtonTxt = "PayNow";
                                tv_continue.setText("Paid Done");
                                tv_continue.setBackgroundColor(getResources().getColor(R.color.detail_circle_green_deep));
                            }
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd'_'HH:mm:ss");
        currentDateandTime = sdf.format(new Date());


        if (LoginActivity.LOGIN_USER.equals("ADMIN")){
            tv_continue.setVisibility(View.INVISIBLE);
        }


       // tv_continue.setText(continueButtonTxt);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_continue.getText().toString().trim().equals("Book Now")){
                    showDialog();
                }
                else if (tv_continue.getText().toString().trim().equals("Pay Now")){
                    Bundle bundle = new Bundle();
                    bundle.putString("thisBookingId", String.valueOf(thisBookingId));
                    Intent intent = new Intent(PropertyDetailsActivity.this, PaymentActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                else if (tv_continue.getText().toString().trim().equals("PendingRe Request")){
                    Toast.makeText(PropertyDetailsActivity.this, "Your Booking Request is Pending", Toast.LENGTH_SHORT).show();
                    return;
                }
/*
                if (tv_continue.getText().toString().trim().equals("Book Now")){
                    if (firebaseAuth.getCurrentUser() != null){
                        String path = currentDateandTime+propertyUid;
                        HashMap<String,Object> bookingMap = new HashMap<>();
                        bookingMap.put("id",path);
                        bookingMap.put("uid",firebaseAuth.getCurrentUser().getUid());
                        bookingMap.put("PropertyId",propertyUid);
                        bookingMap.put("userName",userName);
                        bookingMap.put("userEmail",userEmail);
                        bookingMap.put("userPhone",userPhone);
                        bookingMap.put("userAddress",userAddress);
                        bookingMap.put("isAcceptByAdmin",false);
                        bookingMap.put("isAcceptByPropertyOwner",false);
                        bookingMap.put("isDeleteByPropertyOwner",false);
                        bookingMap.put("isPayment",false);
                        bookingMap.put("paymentMethod","");
                        bookingMap.put("paymentTransactionId","");
                        bookingMap.put("paymentAmountByUser","");
                        bookingMap.put("adminCommissionPercent","");
                        bookingMap.put("adminCommissionAmount","");
                        bookingMap.put("isPaidAdminCommissionAmount",false);
                        bookingMap.put("proOwnerCommissionPercent","");
                        bookingMap.put("proOwnerCommissionAmount","");
                        bookingMap.put("isPaidProOwnerCommissionAmount",false);

                        bookingRef.child(path).setValue(bookingMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    //tv_continue.setText("Pending");
                                    // tv_continue.setBackgroundColor(getResources().getColor(R.color.dialog_color));

                                    Intent intent = new Intent(PropertyDetailsActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });

                    }else {
                        Intent intent = new Intent(PropertyDetailsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                else if (tv_continue.getText().toString().trim().equals("Pay Now")){
                    Bundle bundle = new Bundle();
                    bundle.putString("thisBookingId", String.valueOf(thisBookingId));
                    Intent intent = new Intent(PropertyDetailsActivity.this, PaymentActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                else if (tv_continue.getText().toString().trim().equals("PendingRe Request")){
                    Toast.makeText(PropertyDetailsActivity.this, "Your Booking Request is Pending", Toast.LENGTH_SHORT).show();
                   return;
                }

                */
            }
        });

        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+propertyOwnerNumber));
                startActivity(intent);
            }
        });

    }



        public void showDialog() {

            final Dialog dialog = new Dialog(PropertyDetailsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.booking_dialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView tv_booking_date = (TextView) dialog.findViewById(R.id.tv_booking_date);
            TextView tv_return_date = (TextView) dialog.findViewById(R.id.tv_return_date);
            TextView btn_no = (TextView) dialog.findViewById(R.id.btn_no);
            TextView btn_yes = (TextView) dialog.findViewById(R.id.btn_yes);



            tv_booking_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                    picker = new DatePickerDialog(PropertyDetailsActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    tv_booking_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();





                }
            });
            tv_return_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                    picker = new DatePickerDialog(PropertyDetailsActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    tv_return_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();





                }
            });

            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    booking_date = tv_booking_date.getText().toString().trim();
                    return_date = tv_return_date.getText().toString().trim();

                    if (booking_date.length()>5 && return_date.length()>5)
                    {
                        if (tv_continue.getText().toString().trim().equals("Book Now")){
                            if (firebaseAuth.getCurrentUser() != null){
                                String path = currentDateandTime+propertyUid;
                                HashMap<String,Object> bookingMap = new HashMap<>();
                                bookingMap.put("id",path);
                                bookingMap.put("uid",firebaseAuth.getCurrentUser().getUid());
                                bookingMap.put("PropertyId",propertyUid);
                                bookingMap.put("booking_date",booking_date);
                                bookingMap.put("return_date",return_date);
                                bookingMap.put("PropertyOwnerId",propertyOwnerUid);
                                bookingMap.put("userName",userName);
                                bookingMap.put("userEmail",userEmail);
                                bookingMap.put("userPhone",userPhone);
                                bookingMap.put("userAddress",userAddress);
                                bookingMap.put("isAcceptByAdmin",false);
                                bookingMap.put("isAcceptByPropertyOwner",false);
                                bookingMap.put("isDeleteByPropertyOwner",false);
                                bookingMap.put("isPayment",false);
                                bookingMap.put("paymentMethod","");
                                bookingMap.put("paymentTransactionId","");
                                bookingMap.put("paymentAmountByUser","");
                                bookingMap.put("adminCommissionPercent","");
                                bookingMap.put("adminCommissionAmount","");
                                bookingMap.put("isPaidAdminCommissionAmount",false);
                                bookingMap.put("proOwnerCommissionPercent","");
                                bookingMap.put("proOwnerCommissionAmount","");
                                bookingMap.put("isPaidProOwnerCommissionAmount",false);

                                bookingRef.child(path).setValue(bookingMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            //tv_continue.setText("Pending");
                                            // tv_continue.setBackgroundColor(getResources().getColor(R.color.dialog_color));

                                            Intent intent = new Intent(PropertyDetailsActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });

                            }else {
                                Intent intent = new Intent(PropertyDetailsActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }

                    }else {
                        Toast.makeText(PropertyDetailsActivity.this, "Please choose date", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}