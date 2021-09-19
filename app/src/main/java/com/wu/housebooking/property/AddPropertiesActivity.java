package com.wu.housebooking.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.wu.housebooking.DashboardActivity;
import com.wu.housebooking.R;
import com.wu.housebooking.Util.ItemType;
import com.wu.housebooking.imagePicker.model.Config;
import com.wu.housebooking.imagePicker.model.Image;
import com.wu.housebooking.imagePicker.ui.imagepicker.ImagePicker;
import com.wu.housebooking.model.ItemProperty;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddPropertiesActivity extends AppCompatActivity {
    ItemProperty itemProperty;
    Toolbar toolbar;
    Spinner spinner_cat, spinner_pupose, spinner_fur,spPropertyQualityType;
    ArrayList<ItemType> mListType;
    ArrayList<String> mPropertyName, mPropertyPurpose;
    ImageView img_feature, img_plan, img_gallery;
    RelativeLayout lay_feature, lay_plan, lay_gallery;
    Button lay_submit;
    TextView txtSelect1, txtSelect2, txtSelect3;
    private int REQUEST_FEATURED_PICKER = 2000;
    private int REQUEST_FEATURED_PICKER_PLAN = 2001;
    private int REQUEST_FEATURED_PICKER_GALLERY = 2003;
    boolean isFeatured = false;
    boolean isFeaturedPlan = false;
    boolean isFeaturedGallery = false;
    private ArrayList<Image> featuredImages, featuredImages_plan, featuredImages_gallery = new ArrayList<>();
    ProgressDialog pDialog;
    String strMessage,sttImagePlan,strImageFloor,strImageGallery;
    String srt_type[], srt_fur[];
    EditText edtPurposeName;
    EditText edtPurposeDesc;
    EditText edtPurposePhone;
    EditText edtPurposeAddress;
    EditText edt_discountable_purpose;
    EditText edt_discountable_amount;
    EditText edtPurposeLatitude;
    EditText edtPurposeLongitude;
    EditText edtPurposeBedroom;
    EditText edtPurposeBathroom;
    EditText edtPurposeArea;
    EditText edtPurposeAmenity;
    EditText edtPurposePrice;

    String UId = "";
    String pId = "";
    String propertyPurpose = "";
    String propertyCategory = "";
    String propertyName = "";
    String propertyDescription = "";
    String propertyPhone = "";
    String propertyAddress = "";
    String propertyMapLatitude = "";
    String propertyMapLongitude = "";
    String propertyThumbnailB = "";
    String propertyBed = "";
    String propertyBath = "";
    String propertyArea = "";
    String propertyAmenities = "";
    String propertyPropertyFur = "";
    String propertyQualityType = "";
    String propertyPrice = "";
    String rateAvg = "";
    String propertyFloorPlan = "";
    String propertyTotalRate = "";
    String propertyFur = "";
    boolean isFavourite = false;
    String propertyUid = "";
    String discountablePurpose = "";
    String discountableAmount = "";



    String featured_image = "";
    String floor_plan_image = "";
    String gallery_image = "";
    String currentDateandTime = "";
    FirebaseAuth auth;
    StorageReference mStorageref;
    FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_properties);


        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle!=null)
            propertyUid = bundle.getString("propertyUid");

        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null)
            UId = auth.getCurrentUser().getUid();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_add_properties));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mListType = new ArrayList<>();
        mPropertyName = new ArrayList<>();
        mPropertyPurpose = new ArrayList<>();
        srt_type = getResources().getStringArray(R.array.purpose_array);
        srt_fur = getResources().getStringArray(R.array.fur_array);

        pDialog = new ProgressDialog(AddPropertiesActivity.this);
        spinner_cat = findViewById(R.id.spPropertyType);
        spinner_pupose = findViewById(R.id.spPropertyPurpose);
        spinner_fur = findViewById(R.id.spPropertyFur);
        spPropertyQualityType = findViewById(R.id.spPropertyQualityType);
        edtPurposeName = findViewById(R.id.edt_Purpose_name);
        edtPurposeDesc = findViewById(R.id.edt_Purpose_desc);
        edtPurposePhone = findViewById(R.id.edt_Purpose_phone);

        edtPurposeAddress = findViewById(R.id.edt_Purpose_address);
        edt_discountable_purpose = findViewById(R.id.edt_discountable_purpose);
        edt_discountable_amount = findViewById(R.id.edt_discountable_amount);
        edtPurposeLatitude = findViewById(R.id.edt_Purpose_latitude);
        edtPurposeLongitude = findViewById(R.id.edt_Purpose_longitude);
        edtPurposeBedroom = findViewById(R.id.edt_Purpose_bedroom);
        edtPurposeBathroom = findViewById(R.id.edt_Purpose_bathroom);
        edtPurposeArea = findViewById(R.id.edt_Purpose_area);
        edtPurposeAmenity = findViewById(R.id.edt_Purpose_amenity);
        edtPurposePrice = findViewById(R.id.edt_Purpose_price);
        img_feature = findViewById(R.id.image_add_feature);
        img_plan = findViewById(R.id.image_add_plan);
        img_gallery = findViewById(R.id.image_add_gallery);
        lay_feature = findViewById(R.id.lay_rel_feature);
        lay_plan = findViewById(R.id.lay_rel_plan);
        lay_gallery = findViewById(R.id.lay_rel_gallery);
        lay_submit = findViewById(R.id.btn_sub);
        txtSelect1 = findViewById(R.id.text_select1);
        txtSelect2 = findViewById(R.id.text_select2);
        txtSelect3 = findViewById(R.id.text_select3);
        txtSelect1.setVisibility(View.VISIBLE);
        txtSelect2.setVisibility(View.VISIBLE);
        txtSelect3.setVisibility(View.VISIBLE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd'_'HH:mm:ss");
        currentDateandTime = sdf.format(new Date());


        databaseReference.child(propertyUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                edtPurposeName.setText(""+snapshot.child("propertyName").getValue());
                edtPurposeDesc.setText(""+snapshot.child("propertyDescription").getValue());
                edtPurposePhone.setText(""+snapshot.child("propertyPhone").getValue());
                edtPurposeAddress.setText(""+snapshot.child("propertyAddress").getValue());
                edtPurposeLatitude.setText(""+snapshot.child("propertyMapLatitude").getValue());
                edtPurposeLongitude.setText(""+snapshot.child("propertyMapLongitude").getValue());
                edtPurposeBedroom.setText(""+snapshot.child("propertyBed").getValue());
                edtPurposeBathroom.setText(""+snapshot.child("propertyBath").getValue());
                edtPurposeArea.setText(""+snapshot.child("propertyArea").getValue());
                edtPurposePrice.setText(""+snapshot.child("propertyPrice").getValue());
                edtPurposeAmenity.setText(""+snapshot.child("propertyAmenities").getValue());
                if (String.valueOf(snapshot.child("propertyAmenities").getValue()).length()>10)
                    edt_discountable_purpose.setText(""+snapshot.child("discountablePurpose").getValue());
                if (String.valueOf(snapshot.child("discountablePurpose").getValue()).length()>10)
                    edt_discountable_amount.setText(""+snapshot.child("discountableAmount").getValue());
                if (String.valueOf(snapshot.child("featured_image").getValue()).length()>10)
                    Picasso.get().load(""+snapshot.child("featured_image").getValue()).placeholder(R.drawable.noimage).into(img_feature);
                if (String.valueOf(snapshot.child("floor_plan_image").getValue()).length()>10)
                    Picasso.get().load(""+snapshot.child("floor_plan_image").getValue()).placeholder(R.drawable.noimage).into(img_plan);
                if (String.valueOf(snapshot.child("gallery_image").getValue()).length()>10)
                    Picasso.get().load(""+snapshot.child("gallery_image").getValue()).placeholder(R.drawable.noimage).into(img_gallery);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        String[] propertyTypeItems = new String[] {"Rent",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, propertyTypeItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pupose.setAdapter(adapter);
        spinner_pupose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                propertyPurpose = propertyTypeItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        String[] propertyCatItems = new String[] {"Apartment", "House",};
        ArrayAdapter<String> adapterCat = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, propertyCatItems);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cat.setAdapter(adapterCat);
        spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                propertyCategory = propertyCatItems[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        String[] propertyAmenitiesItems = new String[] {"Furnished", "Semi-Furnished","Unfurnished"};
        ArrayAdapter<String> adapterAmenities = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, propertyAmenitiesItems);
        adapterAmenities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fur.setAdapter(adapterAmenities);
        spinner_fur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                propertyPropertyFur = propertyAmenitiesItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] PropertyQualityTypeItems = new String[] {"Home Premium", "Fretured","Home Latest"};
        ArrayAdapter<String> adapterQualityTypeties = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, PropertyQualityTypeItems);
        adapterQualityTypeties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPropertyQualityType.setAdapter(adapterQualityTypeties);
        spPropertyQualityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                propertyQualityType = PropertyQualityTypeItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        pId = currentDateandTime+UId;
        lay_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propertyName = edtPurposeName.getText().toString().trim();
                propertyDescription = edtPurposeDesc.getText().toString().trim();
                propertyPhone = edtPurposePhone.getText().toString().trim();
                propertyAddress = edtPurposeAddress.getText().toString().trim();
                propertyMapLatitude = edtPurposeLatitude.getText().toString().trim();
                propertyMapLongitude = edtPurposeLongitude.getText().toString().trim();
                propertyThumbnailB = "";
                propertyBed = edtPurposeBedroom.getText().toString().trim();
                propertyBath = edtPurposeBathroom.getText().toString().trim();
                discountablePurpose = edt_discountable_purpose.getText().toString().trim();
                discountableAmount = edt_discountable_amount.getText().toString().trim();
                propertyArea = edtPurposeArea.getText().toString().trim();
                propertyAmenities = edtPurposeAmenity.getText().toString().trim();
                propertyPrice = edtPurposePrice.getText().toString().trim();
                rateAvg = "";
                propertyName = edtPurposeName.getText().toString().trim();

                HashMap<String, Object> propertyMap = new HashMap<>();


                propertyMap.put("UId",UId);
                propertyMap.put("pId",pId);
                propertyMap.put("propertyPurpose",propertyPurpose);
                propertyMap.put("propertyCategory",propertyCategory);
                propertyMap.put("propertyName",propertyName);
                propertyMap.put("propertyDescription",propertyDescription);
                propertyMap.put("propertyPhone",propertyPhone);
                propertyMap.put("propertyAddress",propertyAddress);
                propertyMap.put("propertyMapLatitude",propertyMapLatitude);
                propertyMap.put("propertyMapLongitude",propertyMapLongitude);
                propertyMap.put("discountablePurpose",discountablePurpose);
                propertyMap.put("discountableAmount",discountableAmount);
                propertyMap.put("propertyThumbnailB",propertyThumbnailB);
                propertyMap.put("propertyBed",propertyBed);
                propertyMap.put("propertyBath",propertyBath);
                propertyMap.put("propertyArea",propertyArea);
                propertyMap.put("propertyAmenities",propertyAmenities);
                propertyMap.put("propertyPropertyFur",propertyPropertyFur);
                propertyMap.put("propertyQualityType",propertyQualityType);
                propertyMap.put("propertyPrice",propertyPrice);
                propertyMap.put("rateAvg",rateAvg);
                propertyMap.put("propertyFloorPlan","");
                propertyMap.put("propertyTotalRate","");
                propertyMap.put("propertyFur","");
                propertyMap.put("featured_image",featured_image);
                propertyMap.put("floor_plan_image",floor_plan_image);
                propertyMap.put("gallery_image",gallery_image);
                propertyMap.put("isFav",false);
                propertyMap.put("isApprovedByAdmin",false);
                propertyMap.put("isDeleteByAdmin",false);

                databaseReference.child(pId).setValue(propertyMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddPropertiesActivity.this, "Apartment added successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(AddPropertiesActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });

        lay_feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFeaturedImage();
            }
        });

        lay_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFeaturedImagePlan();
            }
        });

        lay_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFeaturedImageGallery();
            }
        });



    }

    public void chooseFeaturedImage() {
        ImagePicker.with(AddPropertiesActivity.this)
                .setFolderMode(true)
                .setFolderTitle("Album")
                .setImageTitle(getResources().getString(R.string.app_name))
                .setStatusBarColor("#4BA000")
                .setToolbarColor("#4BA000")
                .setProgressBarColor("#4BA000")
                .setMultipleMode(true)
                .setMaxSize(1)
                .setShowCamera(false)
                .setRequestCode(REQUEST_FEATURED_PICKER)
                .start();
    }
    public void chooseFeaturedImagePlan() {
        ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Album")
                .setImageTitle(getResources().getString(R.string.app_name))
                .setStatusBarColor("#008000")
                .setToolbarColor("#008000")
                .setProgressBarColor("#008000")
                .setMultipleMode(true)
                .setMaxSize(1)
                .setShowCamera(false)
                .setRequestCode(REQUEST_FEATURED_PICKER_PLAN)
                .start();
    }
    public void chooseFeaturedImageGallery() {
        ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Album")
                .setImageTitle(getResources().getString(R.string.app_name))
                .setStatusBarColor("#008000")
                .setToolbarColor("#008000")
                .setProgressBarColor("#008000")
                .setMultipleMode(true)
                .setMaxSize(5)
                .setShowCamera(false)
                .setRequestCode(REQUEST_FEATURED_PICKER_GALLERY)
                .start();
    }

    /*private void displayData() {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(AddPropertiesActivity.this, R.layout.spinner_item, mPropertyName);
        typeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_cat.setAdapter(
                new NothingSelectedSpinnerAdapter(typeAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        AddPropertiesActivity.this));
        spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.add_properties_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.add_properties_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        ArrayAdapter<String> typeAdapter2 = new ArrayAdapter<>(AddPropertiesActivity.this, R.layout.spinner_item, srt_type);
        typeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_pupose.setAdapter(
                new NothingSelectedSpinnerAdapter(typeAdapter2,
                        R.layout.contact_spinner_row_nothing_selected_cat,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        AddPropertiesActivity.this));
        spinner_pupose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.add_properties_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.add_properties_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        ArrayAdapter<String> typeAdapterFur = new ArrayAdapter<>(AddPropertiesActivity.this, R.layout.spinner_item, srt_fur);
        typeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_fur.setAdapter(typeAdapterFur);
        spinner_fur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.add_properties_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.add_properties_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FEATURED_PICKER) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                featuredImages = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                strImageFloor= featuredImages.get(0).getPath();
                Uri uri = Uri.fromFile(new File(featuredImages.get(0).getPath()));
                Picasso.get().load(uri).into(img_feature);
                isFeatured = true;
                txtSelect1.setVisibility(View.GONE);
                featuredImageUpload(uri);

            }
        } else if (requestCode == REQUEST_FEATURED_PICKER_PLAN) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                featuredImages_plan = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                Uri uri = Uri.fromFile(new File(featuredImages_plan.get(0).getPath()));
                Picasso.get().load(uri).into(img_plan);
                isFeaturedPlan = true;
                txtSelect2.setVisibility(View.GONE);
                floorPLanImageUpload(uri);

            }
        } else if (requestCode == REQUEST_FEATURED_PICKER_GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                featuredImages_gallery = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
                Uri uri = Uri.fromFile(new File(featuredImages_gallery.get(0).getPath()));
                Picasso.get().load(uri).into(img_gallery);
                isFeaturedGallery = true;
                txtSelect3.setVisibility(View.GONE);
                galleryImageUpload(uri);
            }
        }

    }
    private void featuredImageUpload(Uri featuredImgUri){

        //final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Property");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)

        if (featuredImgUri != null) {
            /*progressDialog = new ProgressDialog(AddPropertiesActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();*/
            //final StorageReference ref = storageReference.child(user_uid+".jpg");
            final StorageReference ref = FirebaseStorage.getInstance().getReference().child("featured_images").child("featured_image"+pId + ".jpg");
            ref.putFile(featuredImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //progressDialog.dismiss();
                    Toast.makeText(AddPropertiesActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        featured_image = uri.toString();
                        //databaseReference.child(pId).child("featured_image").setValue(uri.toString());
                        }
                    });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPropertiesActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    //progressDialog.setMessage("Uploading " + (int) progress + "%");
                }
            });
        }
    }
    private void floorPLanImageUpload(Uri featuredImgUri){

        //final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Property");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)

            if (featuredImgUri != null) {
                /*progressDialog = new ProgressDialog(AddPropertiesActivity.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();*/
                //final StorageReference ref = storageReference.child(user_uid+".jpg");
                final StorageReference ref = FirebaseStorage.getInstance().getReference().child("floor_plan_image").child("floor_plan_image"+pId+ ".jpg");
                ref.putFile(featuredImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //progressDialog.dismiss();
                        Toast.makeText(AddPropertiesActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                floor_plan_image = uri.toString();
                                //databaseReference.child(pId).child("floor_plan_image").setValue(uri.toString());
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPropertiesActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                //progressDialog.setMessage("Uploading " + (int) progress + "%");
                            }
                        });
            }
    }
    private void galleryImageUpload(Uri featuredImgUri){

        //final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Property");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)

            if (featuredImgUri != null) {
                progressDialog = new ProgressDialog(AddPropertiesActivity.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                //final StorageReference ref = storageReference.child(user_uid+".jpg");
                final StorageReference ref = FirebaseStorage.getInstance().getReference().child("gallery_image").child("gallery_image"+pId + ".jpg");
                ref.putFile(featuredImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(AddPropertiesActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                gallery_image = uri.toString();
                                //databaseReference.child(pId).child("gallery_image").setValue(uri.toString());
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddPropertiesActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploading " + (int) progress + "%");
                            }
                        });
            }
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