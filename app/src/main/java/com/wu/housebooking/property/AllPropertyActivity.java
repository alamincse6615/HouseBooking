package com.wu.housebooking.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.R;
import com.wu.housebooking.adapter.AllPropertyAdapter;
import com.wu.housebooking.adapter.FreturedAdapter;
import com.wu.housebooking.adapter.MyPropertyAdapter;
import com.wu.housebooking.model.ItemProperty;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllPropertyActivity extends AppCompatActivity {

    RecyclerView rv_my_property;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ArrayList<ItemProperty> itemPropertyArrayList = new ArrayList<>();
    AllPropertyAdapter allPropertyAdapter;
    private SearchView searchView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_property);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_property));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rv_my_property = findViewById(R.id.rv_my_property);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                itemPropertyArrayList.clear();
                for (DataSnapshot myPropertySnapshot: snapshot.getChildren()) {
                    ItemProperty property = myPropertySnapshot.getValue(ItemProperty.class);
                    itemPropertyArrayList.add(property);
                }
                if (itemPropertyArrayList.size()>0){
                    allPropertyAdapter = new AllPropertyAdapter(AllPropertyActivity.this, itemPropertyArrayList);
                    rv_my_property.setLayoutManager(new LinearLayoutManager(AllPropertyActivity.this, LinearLayoutManager.VERTICAL, false));
                    rv_my_property.setAdapter(allPropertyAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        if (menuItem.getItemId() == R.id.action_search) {
            return true;
        }

        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                allPropertyAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                allPropertyAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return true;
    }
}