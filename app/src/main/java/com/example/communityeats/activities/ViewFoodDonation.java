package com.example.communityeats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.communityeats.R;
import com.example.communityeats.model.FoodDonationItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// Andrew Y.
public class ViewFoodDonation extends AppCompatActivity implements View.OnClickListener{

    private ImageView food_photo;
    private TextView date;
    private TextView item_name;
    private TextView item_description;
    private TextView foodQuanity;
    private Button accept;

    private DatabaseReference reference;
    private FirebaseUser user;
    private String uid;

    FoodDonationItem item;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("FoodDonationItem");
        user = FirebaseAuth.getInstance().getCurrentUser();

        //our xml page
        setContentView(R.layout.view_food_donation);
        //connecting to our XML features
        food_photo = findViewById(R.id.food_photo);
        date = findViewById(R.id.date);
        item_name = findViewById(R.id.food_item_name);
        item_description = findViewById(R.id.description);
        foodQuanity = findViewById(R.id.foodQuanity);
        accept = findViewById(R.id.accept_food_button);
        accept.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null) {
            item = (FoodDonationItem) b.getSerializable("FoodItem");
            String name = b.getString("foodName");
            String foodDate = b.getString("foodDate");
            String description = b.getString("description");
            String quantity = b.getString("quantity");
            String imageURL = b.getString("foodImage");
            uid = b.getString("FoodKey");

            item_name.setText(name);
            date.setText("Date: " + foodDate);
            item_description.setText(description);
            foodQuanity.setText("Quantity: " + quantity);
            Glide.with(this).load(imageURL).into(food_photo);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        startActivity(new Intent(ViewFoodDonation.this, HomeScreenActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_food:
                        startActivity(new Intent(ViewFoodDonation.this, FoodDonationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(ViewFoodDonation.this, ViewUserProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.accept_food_button){
            acceptFood();
        }
    }

    private void acceptFood() {
        System.out.println("Button clicked");
        String userUid = user.getUid();
        reference.child(uid).child("recipientID").setValue(userUid);
        //item.setRecipientID(userUid);
        startActivity(new Intent(ViewFoodDonation.this, HomeScreenActivity.class));
    }
}
