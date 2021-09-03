package com.wu.housebooking.admin.ui.dashboard;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.R;
import com.wu.housebooking.adapter.MyPropertyAdapter;
import com.wu.housebooking.adapter.PropertyRequesteAdapter;
import com.wu.housebooking.model.ItemProperty;
import com.wu.housebooking.property.MyPropertyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class PropertyRequestFragment extends Fragment {
    RecyclerView rv_requested_property_list;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ArrayList<ItemProperty> itemPropertyArrayList = new ArrayList<>();
    PropertyRequesteAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_property_request, container, false);
        rv_requested_property_list = root.findViewById(R.id.rv_requested_property_list);


        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                itemPropertyArrayList.clear();
                for (DataSnapshot myPropertySnapshot: snapshot.getChildren()) {
                    if (!Boolean.parseBoolean(String.valueOf(myPropertySnapshot.child("isFav").getValue()))){
                        ItemProperty property = myPropertySnapshot.getValue(ItemProperty.class);
                        itemPropertyArrayList.add(property);
                    }
                }
                if (itemPropertyArrayList.size()>0){
                    adapter = new PropertyRequesteAdapter(getActivity(), itemPropertyArrayList);
                    rv_requested_property_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_requested_property_list.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





        return root;
    }

}