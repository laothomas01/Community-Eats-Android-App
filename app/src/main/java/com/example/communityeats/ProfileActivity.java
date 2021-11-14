package com.example.communityeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    //XML features
    private ImageView profilepicTxt;
    private TextView emailTxt;
    private TextView usernameTxt;
    private TextView addressTxt;
    private TextView phonenumTxt;

    //debug TAG
    private static final String TAG = ProfileActivity.class.getSimpleName();

    //accessing firebase database
    private static final String USER = "User";
    private FirebaseUser user;
    private DatabaseReference reference;

    private String email;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //find our XML features
        emailTxt = findViewById(R.id.display_email);
        usernameTxt = findViewById(R.id.display_username);
        addressTxt = findViewById(R.id.display_address);

        //access firebase currently logged in user
        user = FirebaseAuth.getInstance().getCurrentUser();
        //get the ID of our current user because we do not have usernames.
        String uid = user.getUid();
        //reference to get our User node
        reference = FirebaseDatabase.getInstance().getReference(USER);
        //reference the children of the user node.

        //if we get the userID, add a listener
        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //if you successfully got the task
                if (task.isSuccessful()) {
                    //if the user exists
                    if (task.getResult().exists()) {
                        //toast
                        Toast.makeText(ProfileActivity.this, "Successful Read", Toast.LENGTH_SHORT).show();
                        //create a snapshot of that data
                        DataSnapshot ds = task.getResult();
                        //get the email from the user node.
                        String email = String.valueOf(ds.child("email").getValue());
                        String username = String.valueOf(ds.child("username").getValue());
                        String address = String.valueOf(ds.child("address").getValue());

                        emailTxt.setText(email);
                        usernameTxt.setText(username);
                        addressTxt.setText(address);

                    } else {
                        Toast.makeText(ProfileActivity.this, "User Not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        System.out.println(uid);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        startActivity(new Intent(ProfileActivity.this, HomeScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_food:
                        startActivity(new Intent(ProfileActivity.this, FoodDonationActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }
}

//