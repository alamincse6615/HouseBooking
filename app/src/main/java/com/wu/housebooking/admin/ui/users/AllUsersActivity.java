package com.wu.housebooking.admin.ui.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.R;
import com.wu.housebooking.adapter.AllUsersAdapter;
import com.wu.housebooking.model.SignUpModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity {
    AllUsersAdapter adapter;

    RecyclerView all_users;
    ArrayList<SignUpModel> signUpModelArrayList  = new ArrayList<>();
    private DatabaseReference mDatabase;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_users));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        all_users = findViewById(R.id.rv_all_users);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                signUpModelArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    SignUpModel signUpModel = dataSnapshot.getValue(SignUpModel.class);
                    signUpModelArrayList.add(signUpModel);
                }
                if (signUpModelArrayList.size()>0){
                    adapter = new AllUsersAdapter(AllUsersActivity.this,signUpModelArrayList);
                    all_users.setLayoutManager(new LinearLayoutManager(AllUsersActivity.this, LinearLayoutManager.VERTICAL, false));
                    all_users.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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