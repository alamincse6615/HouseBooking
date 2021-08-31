package com.wu.housebooking;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wu.housebooking.Util.ContuctUsActivity;
import com.wu.housebooking.auth.LoginActivity;
import com.wu.housebooking.auth.ProfileEditActivity;
import com.wu.housebooking.carvelayout.SpaceOnClickListener;
import com.wu.housebooking.databinding.ActivityDashboardBinding;
import com.wu.housebooking.fragment.HomeFragment;
import com.wu.housebooking.property.AddPropertiesActivity;
import com.wu.housebooking.property.MyPropertyActivity;

import org.jetbrains.annotations.NotNull;

public class DashboardActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    private FragmentManager fragmentManager;
   // public Space spaceNavigationView;
    private DrawerLayout drawerLayout;
    public SpaceNavigationView spaceNavigationView;
    private ActivityDashboardBinding binding;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    String UserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        fragmentManager = getSupportFragmentManager();
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_row);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.Container, homeFragment).commit();

        spaceNavigationView = findViewById(R.id.space);


        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.menu_home), R.drawable.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.menu_latest), R.drawable.ic_latest));
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.menu_favourite), R.drawable.ic_favourite));
        spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.menu_profile), R.drawable.ic_profile));
        //spaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.menu_setting), R.drawable.ic_setting));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {

                toolbar.setTitle(getString(R.string.menu_home));
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.Container, homeFragment).commit();
                highLightNavigation(0, getString(R.string.menu_home));
                /*if (MyApp.getIsLogin()) {


                    Intent intent = new Intent(MainActivity.this, AddPropertiesActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                    finish();
                }*/
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        toolbar.setTitle(getString(R.string.menu_home));
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, homeFragment).commit();
                        highLightNavigation(0, getString(R.string.menu_home));
                        break;
                    case 1:
                        toolbar.setTitle(getString(R.string.menu_latest));
                        HomeFragment latestFragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, latestFragment).commit();
                        highLightNavigation(1, getString(R.string.menu_latest));
                        break;
                    case 2:
                        toolbar.setTitle(getString(R.string.menu_favourite));
                        HomeFragment favouriteFragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, favouriteFragment).commit();
                        highLightNavigation(3, getString(R.string.menu_favourite));
                        break;
                    case 3:
                        if (firebaseAuth.getCurrentUser() != null){
                            Intent intent = new Intent(DashboardActivity.this, ProfileEditActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        /*toolbar.setTitle(getString(R.string.menu_setting));
                        HomeFragment settingFragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, settingFragment).commit();
                        highLightNavigation(8, getString(R.string.menu_setting));*/
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.menu_go_home:
                        toolbar.setTitle(getString(R.string.menu_home));
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, homeFragment).commit();
                        spaceNavigationView.changeCurrentItem(0);
                        return true;
                    case R.id.menu_go_latest:
                        toolbar.setTitle(getString(R.string.menu_latest));
                        HomeFragment homeFragment1 = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, homeFragment1).commit();
                        spaceNavigationView.changeCurrentItem(1);
                        return true;
                    case R.id.menu_go_property:
                        toolbar.setTitle(getString(R.string.menu_property));
                        HomeFragment homeFragment2 = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, homeFragment2).commit();
                        spaceNavigationView.changeCurrentItem(-1);
                        return true;
                    case R.id.menu_go_favourite:
                        toolbar.setTitle(getString(R.string.menu_favourite));
                        HomeFragment homeFragment3 = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, homeFragment3).commit();
                        spaceNavigationView.changeCurrentItem(2);
                        return true;
                    case R.id.menu_go_my_properties:
                        if (firebaseAuth.getCurrentUser() != null){
                            Intent intent = new Intent(DashboardActivity.this, MyPropertyActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        //spaceNavigationView.changeCurrentItem(-1);
                        return true;
                    case R.id.menu_go_add_properties:
                        if (firebaseAuth.getCurrentUser() != null){
                            Intent intent = new Intent(DashboardActivity.this, AddPropertiesActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;

                    case R.id.menu_go_profile:
                        if (firebaseAuth.getCurrentUser() != null){
                            Intent intent = new Intent(DashboardActivity.this, ProfileEditActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        return true;
                    case R.id.menu_go_contact:
                        if (firebaseAuth.getCurrentUser() != null){
                            Intent intent = new Intent(DashboardActivity.this, ContuctUsActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    case R.id.menu_go_setting:
                        toolbar.setTitle(getString(R.string.menu_setting));
                        HomeFragment homeFragment4 = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.Container, homeFragment4).commit();
                        spaceNavigationView.changeCurrentItem(3);
                        return true;
                    case R.id.menu_go_login:
                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        startActivity(intent);                        return true;
                    case R.id.menu_go_logout:
                        new AlertDialog.Builder(DashboardActivity.this)
                                .setTitle("Logout ")
                                .setCancelable(false)
                                .setIcon(R.drawable.imagepicker_ic_back)
                                .setMessage("Are You Sure Want to Logout ?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        if (firebaseAuth.getCurrentUser() != null){
                                            firebaseAuth.signOut();
                                            finish();
                                        }

                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();

                        return true;
                    default:
                        return true;
                }
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (firebaseAuth.getCurrentUser() != null) {
            navigationView.getMenu().findItem(R.id.menu_go_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_go_logout).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.menu_go_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_go_logout).setVisible(false);
        }

        if (firebaseAuth.getCurrentUser() == null) {
            navigationView.getMenu().findItem(R.id.menu_go_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_go_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_go_my_properties).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_go_add_properties).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_go_logout).setVisible(false);
        }
        navigationView.getMenu().findItem(R.id.menu_go_setting).setVisible(false);
        if (firebaseAuth.getCurrentUser() != null)
        mDatabase.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserType = String.valueOf(snapshot.child("userType").getValue());
                if (UserType.equals("user")){
                    navigationView.getMenu().findItem(R.id.menu_go_my_properties).setVisible(false);
                    navigationView.getMenu().findItem(R.id.menu_go_add_properties).setVisible(false);
                }
                if (UserType.equals("owner")){
                    navigationView.getMenu().findItem(R.id.menu_go_my_properties).setVisible(true);
                    navigationView.getMenu().findItem(R.id.menu_go_add_properties).setVisible(true);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.d("error",error.getMessage());

            }
        });


       /* BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);*/

    }

    public void highLightNavigation(int position, String name) {

        navigationView.getMenu().getItem(position).setChecked(true);
        toolbar.setTitle(name);
    }

}