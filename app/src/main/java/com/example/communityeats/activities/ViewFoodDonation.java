package com.example.communityeats.activities;

import android.os.Bundle;
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
    private TextView recipient;
    private Button accept;

    private DatabaseReference reference;
    private FirebaseUser user;
    private String uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("FoodDonationItem");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = reference.getKey();

        accept = (Button) findViewById(R.id.logoutBtn);
        accept.setOnClickListener(this);

        //our xml page
        setContentView(R.layout.view_food_donation);
        //connecting to our XML features
        food_photo = findViewById(R.id.food_photo);
        date = findViewById(R.id.date);
        item_name = findViewById(R.id.food_item_name);
        item_description = findViewById(R.id.description);
        foodquanity = findViewById(R.id.foodQuanity);
        recipient = findViewById(R.id.recipientID);
        accept = findViewById(R.id.accept_food_button);

        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>(){

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //if you successfully got the task
                if (task.isSuccessful()) {
                    //if the user exists
                    if (task.getResult().exists()) {
                        //toast
                        Toast.makeText(ViewFoodDonation.this, "Successful Read", Toast.LENGTH_SHORT).show();
                        //create a snapshot of that data
                        DataSnapshot ds = task.getResult();
                        //get the email from the user node.
                        String imageUrl = String.valueOf(ds.child("foodImageUrl").getValue());
                        String foodDate = String.valueOf(ds.child("date").getValue());
                        String foodName = String.valueOf(ds.child("foodName").getValue());
                        String foodDescription = String.valueOf(ds.child("foodDescription").getValue());
                        String foodQuanity = String.valueOf(ds.child("foodQuantity").getValue());
                        String recipientID = String.valueOf(ds.child("recipientID").getValue());

                        //Setting the data from firebase into XML.
                        date.setText(foodDate);
                        item_name.setText(foodName);
                        item_description.setText(foodDescription);
                        foodquanity.setText(foodQuanity);
                        recipient.setText(recipientID);
                        //Glide is for getting the image from firebase into xml.
                        Glide.with(ViewFoodDonation.this).load(imageUrl).into(food_photo);

                    }
                    else {
                        Toast.makeText(ViewFoodDonation.this, "User Not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ViewFoodDonation.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
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
        String userUid = user.getUid();
        reference.child(uid).child("recipientID").setValue(userUid);

    }
}
