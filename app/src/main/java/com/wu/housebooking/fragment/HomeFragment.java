package com.wu.housebooking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.R;
import com.wu.housebooking.adapter.AllPropertyAdapter;
import com.wu.housebooking.adapter.FreturedAdapter;
import com.wu.housebooking.adapter.HomeLatestAdapter;
import com.wu.housebooking.adapter.HomePremiumAdapter;
import com.wu.housebooking.model.ItemProperty;
import com.wu.housebooking.property.AllPropertyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView propertyQualityType;
    RecyclerView rv_fretured,rv_homePremium,rv_home_latest;
    ArrayList<ItemProperty> itemFreturedPropertyArrayList = new ArrayList<>();
    ArrayList<ItemProperty> itemHomePremiumPropertyArrayList = new ArrayList<>();
    ArrayList<ItemProperty> itemHomeLatestPropertyArrayList = new ArrayList<>();
    ArrayList<ItemProperty> itemArrayList = new ArrayList<>();
    FreturedAdapter freturedAdapter;
    HomePremiumAdapter homePremiumAdapter;
    HomeLatestAdapter homeLatestAdapter;
    DatabaseReference databaseReference;
    EditText search_property;
    AllPropertyAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        rv_fretured = rootView.findViewById(R.id.rv_fretured);
        rv_homePremium = rootView.findViewById(R.id.rv_homePremium);
        rv_home_latest = rootView.findViewById(R.id.rv_home_latest);
        search_property = rootView.findViewById(R.id.edt_name);
        search_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllPropertyActivity.class);
                startActivity(intent);
            }
        });

        /*search_property.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });*/

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                itemFreturedPropertyArrayList.clear();
                if (snapshot != null){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        ItemProperty property = postSnapshot.getValue(ItemProperty.class);
                        if (property!= null){
                            itemArrayList.add(property);

                            if (property.getPropertyQualityType().equals("Fretured") && (Boolean) postSnapshot.child("isApprovedByAdmin").getValue())
                                itemFreturedPropertyArrayList.add(property);
                            if (property.getPropertyQualityType().equals("Home Premium") && (Boolean) postSnapshot.child("isApprovedByAdmin").getValue())
                                itemHomePremiumPropertyArrayList.add(property);
                            if (property.getPropertyQualityType().equals("Home Latest") && (Boolean) postSnapshot.child("isApprovedByAdmin").getValue())
                                itemHomeLatestPropertyArrayList.add(property);
                        }
                    }
                    if (itemFreturedPropertyArrayList.size()>0){
                        freturedAdapter = new FreturedAdapter(getActivity(), itemFreturedPropertyArrayList);
                        rv_fretured.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        rv_fretured.setAdapter(freturedAdapter);
                    }

                    if (itemHomePremiumPropertyArrayList.size()>0){
                        homePremiumAdapter = new HomePremiumAdapter(getActivity(), itemHomePremiumPropertyArrayList);
                        rv_homePremium.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        rv_homePremium.setAdapter(homePremiumAdapter);
                    }

                    if (itemHomeLatestPropertyArrayList.size()>0){
                        homeLatestAdapter = new HomeLatestAdapter(getActivity(), itemHomeLatestPropertyArrayList);
                        rv_home_latest.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        rv_home_latest.setAdapter(homeLatestAdapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return rootView;
    }
   /* private void filter(String text) {
        ArrayList<ItemProperty> filteredList = new ArrayList<>();
        for (ItemProperty item : itemArrayList) {
            if (item.getPropertyName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }*/
}