package com.wu.housebooking.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
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
import java.util.Date;
import java.util.HashMap;

public class PropertyDetailsActivity extends AppCompatActivity {

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
    String currentDateandTime = "";
    String thisBookingId = "";
    String continueButtonTxt = "Rent";
    String propertyOwnerNumber = "";
    String userName = "";
    String userPhone = "";
    String userEmail = "";
    String userAddress = "";


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
                        if(Boolean.parseBoolean(String.valueOf(PropertySnapshot.child("isAcceptByPropertyOwner").getValue()))){
                            thisBookingId = String.valueOf(PropertySnapshot.child("id").getValue());
                            continueButtonTxt = "PayNow";
                            tv_continue.setText("Pay Now");
                            tv_continue.setBackgroundColor(getResources().getColor(R.color.detail_circle_green));
                        }else {
                            continueButtonTxt = "PendingRe Request";
                            tv_continue.setText("PendingRe Request");
                            tv_continue.setBackgroundColor(getResources().getColor(R.color.yellow));
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

       // tv_continue.setText(continueButtonTxt);
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}