package com.wu.housebooking.admin.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.R;
import com.wu.housebooking.adapter.AdminTransactionAdapter;
import com.wu.housebooking.adapter.BookingRequestToPOAdapter;
import com.wu.housebooking.model.BookingModel;
import com.wu.housebooking.property.MyPropertyDetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class TransactionRequestFragment extends Fragment {
    RecyclerView transaction_list;
    DatabaseReference bookingRef;
    ArrayList<BookingModel> bookingModelArrayList = new ArrayList<>();
    AdminTransactionAdapter adminTransactionAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_transaction_request, container, false);
        transaction_list = root.findViewById(R.id.transaction_list);
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
                    adminTransactionAdapter = new AdminTransactionAdapter(getActivity(), bookingModelArrayList);
                    transaction_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    transaction_list.setAdapter(adminTransactionAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        return root;
    }


}