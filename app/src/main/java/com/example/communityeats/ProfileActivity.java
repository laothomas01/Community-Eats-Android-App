package com.example.communityeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilepicTxt;
    private TextView emailTxt;
    private TextView usernameTxt;
    private TextView addressTxt;
    private TextView phonenumTxt;


    private int username;
    private int address;
    private int email;
//    private int phonenum;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USER = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
//        Bundle bundle = intent.getExtras();
//        if(bundle != null){
//            email = bundle.getInt("email");
//            username = bundle.getInt("username");
//            address = bundle.getInt("address");
//        }
        emailTxt = findViewById(R.id.email);
//        emailTxt.setText(email);
        usernameTxt = findViewById(R.id.username);
//        usernameTxt.setText(username);
//        phonenumTxt = findViewById(R.id.phonenumber);
//        phonenumTxt.setText(phonenum);
        profilepicTxt = findViewById(R.id.profilepic);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(email)) {
                       emailTxt.setText(ds.child("email").getValue(String.class));
                       usernameTxt.setText(ds.child("username").getValue(String.class));
                        addressTxt.setText(ds.child("address").getValue(String.class));
//                        phonenumTxt.setText(ds.child("phonenumber").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        startActivity(new Intent(ProfileActivity.this, HomeScreenActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_food:
                        startActivity(new Intent(ProfileActivity.this, FoodDonationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
}