package com.wu.housebooking.admin.ui.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.R;
import com.wu.housebooking.adapter.AdminTransactionAdapter;
import com.wu.housebooking.model.BookingModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    RecyclerView transaction_list;
    DatabaseReference bookingRef;
    ArrayList<BookingModel> bookingModelArrayList = new ArrayList<>();
    AdminTransactionAdapter adminTransactionAdapter;
    Toolbar toolbar;
    TextView no_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        no_transaction = findViewById(R.id.no_transaction);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_transaction));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        transaction_list = findViewById(R.id.transaction_list);
        bookingRef = FirebaseDatabase.getInstance().getReference("Booking");

        bookingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot bSnapshot : snapshot.getChildren()){
                    BookingModel model = bSnapshot.getValue(BookingModel.class);
                    if (model != null && Boolean.parseBoolean(String.valueOf(bSnapshot.child("isPayment").getValue())) && !Boolean.parseBoolean(String.valueOf(bSnapshot.child("isPaidAdminCommissionAmount").getValue()))) {
                        bookingModelArrayList.add(model);
                    }

                }
                if (bookingModelArrayList.size()>0){
                    no_transaction.setVisibility(View.GONE);
                    transaction_list.setVisibility(View.VISIBLE);
                    adminTransactionAdapter = new AdminTransactionAdapter(TransactionActivity.this, bookingModelArrayList);
                    transaction_list.setLayoutManager(new LinearLayoutManager(TransactionActivity.this, LinearLayoutManager.VERTICAL, false));
                    transaction_list.setAdapter(adminTransactionAdapter);
                }else {
                    no_transaction.setVisibility(View.VISIBLE);
                    transaction_list.setVisibility(View.GONE);
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