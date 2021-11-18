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
import com.example.communityeats.model.FoodDonationItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// Andrew Y.
public class ViewFoodDonation extends AppCompatActivity implements View.OnClickListener{

    private ImageView food_photo;
    private TextView date;
    private TextView item_name;
    private TextView item_description;
    private TextView foodquanity;
    private Button accept;

    private DatabaseReference reference;
    private FirebaseUser user;
    private String uid;

    FoodDonationItem item;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("FoodDonationItem");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = reference.getKey();

        //our xml page
        setContentView(R.layout.view_food_donation);
        //connecting to our XML features
        food_photo = findViewById(R.id.food_photo);
        date = findViewById(R.id.date);
        item_name = findViewById(R.id.food_item_name);
        item_description = findViewById(R.id.description);
        foodquanity = findViewById(R.id.foodQuanity);
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

            item_name.setText(name);
            date.setText(foodDate);
            item_description.setText(description);
            foodquanity.setText(quantity);
            Glide.with(this).load(imageURL).into(food_photo);
        }

//        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>(){
//
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                //if you successfully got the task
//                if (task.isSuccessful()) {
//                    //if the user exists
//                    if (task.getResult().exists()) {
//                        //toast
//                        Toast.makeText(ViewFoodDonation.this, "Successful Read", Toast.LENGTH_SHORT).show();
//                        //create a snapshot of that data
//                        DataSnapshot ds = task.getResult();
//                        //get the email from the user node.
//                        String imageUrl = String.valueOf(ds.child("foodImageUrl").getValue());
//                        String foodDate = String.valueOf(ds.child("date").getValue());
//                        String foodName = String.valueOf(ds.child("foodName").getValue());
//                        String foodDescription = String.valueOf(ds.child("foodDescription").getValue());
//                        String foodQuanity = String.valueOf(ds.child("foodQuantity").getValue());
//                        String recipientID = String.valueOf(ds.child("recipientID").getValue());
//
//                        //Setting the data from firebase into XML.
//                        date.setText(foodDate);
//                        item_name.setText(foodName);
//                        item_description.setText(foodDescription);
//                        foodquanity.setText(foodQuanity);
//                        recipient.setText(recipientID);
//                        //Glide is for getting the image from firebase into xml.
//                        Glide.with(ViewFoodDonation.this).load(imageUrl).into(food_photo);
//
//                    }
//                    else {
//                        Toast.makeText(ViewFoodDonation.this, "User Not exist", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    Toast.makeText(ViewFoodDonation.this, "Failed to read", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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
        //reference.child(uid).child("recipientID").setValue(userUid);
        item.setRecipientID(userUid);
        startActivity(new Intent(ViewFoodDonation.this, HomeScreenActivity.class));
    }
}
