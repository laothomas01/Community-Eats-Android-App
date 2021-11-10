package com.example.communityeats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.communityeats.R;
import com.example.communityeats.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment selectedFragment = null;
                switch(id){
                    case R.id.nav_home:
                        startActivity(new Intent(HomeScreenActivity.this, HomeScreenActivity.class));
                        overridePendingTransition(0,0);
                       return true;
                    case R.id.nav_food:
                        startActivity(new Intent(HomeScreenActivity.this, FoodDonationActivity.class));
                        overridePendingTransition(0,0);
                       return true;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                            break;
//                        startActivity(new Intent(HomeScreenActivity.this, ProfileFragment.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

//                return false;
                return false;
            }
        });





    }
}
