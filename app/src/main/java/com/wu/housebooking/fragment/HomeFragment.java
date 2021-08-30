package com.wu.housebooking.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.wu.housebooking.adapter.FreturedAdapter;
import com.wu.housebooking.adapter.HomeLatestAdapter;
import com.wu.housebooking.adapter.HomePremiumAdapter;
import com.wu.housebooking.model.ItemProperty;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView propertyQualityType;
    RecyclerView rv_fretured,rv_homePremium,rv_home_latest;
    ArrayList<ItemProperty> itemFreturedPropertyArrayList = new ArrayList<>();
    ArrayList<ItemProperty> itemHomePremiumPropertyArrayList = new ArrayList<>();
    ArrayList<ItemProperty> itemHomeLatestPropertyArrayList = new ArrayList<>();
    FreturedAdapter freturedAdapter;
    HomePremiumAdapter homePremiumAdapter;
    HomeLatestAdapter homeLatestAdapter;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        rv_fretured = rootView.findViewById(R.id.rv_fretured);
        rv_homePremium = rootView.findViewById(R.id.rv_homePremium);
        rv_home_latest = rootView.findViewById(R.id.rv_home_latest);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                itemFreturedPropertyArrayList.clear();
                if (snapshot != null){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        ItemProperty property = postSnapshot.getValue(ItemProperty.class);
                        if (property!= null){
                            if (property.getPropertyQualityType().equals("Fretured"))
                                itemFreturedPropertyArrayList.add(property);
                            if (property.getPropertyQualityType().equals("Home Premium"))
                                itemHomePremiumPropertyArrayList.add(property);
                            if (property.getPropertyQualityType().equals("Home Latest"))
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
}