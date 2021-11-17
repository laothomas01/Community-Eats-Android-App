package com.example.communityeats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.communityeats.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewUserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    //XML features
    private ImageView profilePic;
    private TextView emailTxt;
    private TextView usernameTxt;
    private TextView addressTxt;
    private Button update; //update Button
    private Button logout; //logout Button

    //debug TAG
    private static final String TAG = ViewUserProfileActivity.class.getSimpleName();

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

        logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(this);

        //find our XML features
        emailTxt = findViewById(R.id.display_email);
        usernameTxt = findViewById(R.id.display_username);
        addressTxt = findViewById(R.id.display_address);
        update = findViewById(R.id.update_button);
        profilePic = findViewById(R.id.user_profile_image);

        update.setOnClickListener(this);
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
                        Toast.makeText(ViewUserProfileActivity.this, "Successful Read", Toast.LENGTH_SHORT).show();
                        //create a snapshot of that data
                        DataSnapshot ds = task.getResult();
                        //get the email from the user node.
                        String email = String.valueOf(ds.child("email").getValue());
                        String username = String.valueOf(ds.child("username").getValue());
                        String address = String.valueOf(ds.child("address").getValue());
                        String imageUrl = String.valueOf(ds.child("imageURL").getValue());


                        emailTxt.setText(email);
                        usernameTxt.setText(username);
                        addressTxt.setText(address);

                        Glide.with(ViewUserProfileActivity.this).load(imageUrl).into(profilePic);

                    } else {
                        Toast.makeText(ViewUserProfileActivity.this, "User Not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ViewUserProfileActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Glide.with(this).load(imageUrl).into(profilePic);

//
//        System.out.println(uid);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        startActivity(new Intent(ViewUserProfileActivity.this, HomeScreenActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_food:
                        startActivity(new Intent(ViewUserProfileActivity.this, FoodDonationActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(ViewUserProfileActivity.this, ViewUserProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_button:
                startActivity(new Intent(ViewUserProfileActivity.this, UpdateProfileActivity.class));
            case R.id.logoutBtn: //if user clicks on logout button
                logOut();
        }
    }
    //logout function
    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ViewUserProfileActivity.this, LoginActivity.class));
        Toast.makeText(ViewUserProfileActivity.this, "Logged out. See you soon!", Toast.LENGTH_LONG).show();
    }
}