package com.wu.housebooking.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wu.housebooking.R;
import com.wu.housebooking.adapter.BookingRequestToPOAdapter;
import com.wu.housebooking.adapter.MyPropertyAdapter;
import com.wu.housebooking.model.BookingModel;
import com.wu.housebooking.model.ItemProperty;
import com.wu.housebooking.model.SignUpModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyPropertyDetailsActivity extends AppCompatActivity {
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

    TextView text;
    TextView tv_edit;
    TextView textPrice;
    TextView textFur;
    TextView  textVery;
    TextView  property_description;
    TextView  ContainerAmenities;
    TextView  tv_continue;
    ImageView image_floor;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,bookingRef,userRef;
    String propertyUid = "";
    String currentDateandTime = "";
    String thisBookingId = "";
    String continueButtonTxt = "Rent";
    RecyclerView rv_request_list;

    ArrayList<BookingModel> bookingModelArrayList = new ArrayList<>();
    ArrayList<SignUpModel> signUpModelArrayList = new ArrayList<>();
    BookingRequestToPOAdapter bookingRequestToPOAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_property_details);



        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_my_properties));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle!=null)
            propertyUid = bundle.getString("propertyUid");

        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        bookingRef = FirebaseDatabase.getInstance().getReference("Booking");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        firebaseAuth = FirebaseAuth.getInstance();

        ivGallery = findViewById(R.id.ivGallery);
        textAddress = findViewById(R.id.textAddress);
        textPhone = findViewById(R.id.textPhone);
        textPrice = findViewById(R.id.textPrice);
        text = findViewById(R.id.text);
        tv_edit = findViewById(R.id.tv_edit);
        rv_request_list = findViewById(R.id.rv_request_list);
        databaseReference.child(propertyUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (String.valueOf(snapshot.child("gallery_image").getValue()).length()>5)
                    Picasso.get().load(String.valueOf(snapshot.child("gallery_image").getValue())).into(ivGallery);
                text.setText(String.valueOf(snapshot.child("propertyName").getValue()));
                textPrice.setText(String.valueOf(snapshot.child("propertyPrice").getValue()));
                textAddress.setText(String.valueOf(snapshot.child("propertyAddress").getValue()));
                textPhone.setText(String.valueOf(snapshot.child("propertyPhone").getValue()));

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        bookingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot bookingSnapshot) {
                for (DataSnapshot bSnapshot : bookingSnapshot.getChildren()){
                    BookingModel model = bSnapshot.getValue(BookingModel.class);
                    if (model != null && propertyUid.equals(model.getPropertyId()) && model.isAcceptByPropertyOwner() && model.isDeleteByPropertyOwner()) {
                        bookingModelArrayList.add(model);
                    }

                }
                if (bookingModelArrayList.size()>0){
                    bookingRequestToPOAdapter = new BookingRequestToPOAdapter(MyPropertyDetailsActivity.this, bookingModelArrayList);
                    rv_request_list.setLayoutManager(new LinearLayoutManager(MyPropertyDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
                    rv_request_list.setAdapter(bookingRequestToPOAdapter);
                }
                /*if (snapshot.child("propertyUid").getValue().equals(propertyUid)){




                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        BookingModel bookingModel = dataSnapshot.getValue(BookingModel.class);
                        bookingModelArrayList.add(bookingModel);
                    }
                    if (bookingModelArrayList.size()>0){
                        bookingRequestToPOAdapter = new MyPropertyAdapter(MyPropertyDetailsActivity.this, bookingModelArrayList);
                        rv_request_list.setLayoutManager(new LinearLayoutManager(MyPropertyDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
                        rv_request_list.setAdapter(bookingRequestToPOAdapter);
                    }
                }*/
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Bundle bundle2 = new Bundle();
        bundle2.putString("propertyUid", String.valueOf(propertyUid));

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPropertyDetailsActivity.this,AddPropertiesActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
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