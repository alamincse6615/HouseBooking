package com.wu.housebooking.admin.ui.users;

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
import com.wu.housebooking.adapter.AllUsersAdapter;
import com.wu.housebooking.model.SignUpModel;
import com.wu.housebooking.property.AllPropertyActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllUsersFragment  extends Fragment {
    AllUsersAdapter adapter;

    RecyclerView all_users;
    ArrayList<SignUpModel> signUpModelArrayList  = new ArrayList<>();
    private DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        View root = inflater.inflate(R.layout.fragment_all_users, container, false);
        all_users = root.findViewById(R.id.rv_all_users);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                signUpModelArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    SignUpModel signUpModel = dataSnapshot.getValue(SignUpModel.class);
                    signUpModelArrayList.add(signUpModel);
                }
                if (signUpModelArrayList.size()>0){
                    adapter = new AllUsersAdapter(getActivity(),signUpModelArrayList);
                    all_users.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    all_users.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return root;
    }
}
