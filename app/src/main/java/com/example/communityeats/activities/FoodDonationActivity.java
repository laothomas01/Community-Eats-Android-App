package com.example.communityeats.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.communityeats.R;
import com.example.communityeats.model.FoodDonationItem;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class FoodDonationActivity extends AppCompatActivity implements View.OnClickListener {

    private String userName;
    private Button submitFoodDonation;
    private EditText itemName, itemDescription, itemQuantity;
    private ImageView itemImage;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String itemKey, downloadImageUrl;
    private String saveCurrentDate;
    private StorageReference itemImagesRef;

    String name, quantity, description;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_donation);

        submitFoodDonation = (Button) findViewById(R.id.Submit);
        submitFoodDonation.setOnClickListener(this);
        itemName = (EditText) findViewById(R.id.itemName);
        itemQuantity = (EditText) findViewById(R.id.quantity);
        itemDescription = (EditText) findViewById(R.id.description);
        itemImage = (ImageView) findViewById(R.id.food_placeholder_image);
        itemImage.setOnClickListener(this);

        itemImagesRef = FirebaseStorage.getInstance().getReference().child("Food Images");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_food);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        startActivity(new Intent(FoodDonationActivity.this, HomeScreenActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_food:
                        startActivity(new Intent(FoodDonationActivity.this, FoodDonationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(FoodDonationActivity.this, UserProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.food_placeholder_image:
                openGallery();

            case R.id.Submit:
                submitDonation();
        }

    }

    private void submitDonation() {
        name = itemName.getText().toString().trim();
        quantity = itemQuantity.getText().toString().trim();
        description = itemDescription.getText().toString().trim();


        if (imageUri == null) {
            Toast.makeText(this,"Item image is required. Please upload image.", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(name)) {
            Toast.makeText(this,"Please enter food item name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(quantity)) {
            Toast.makeText(this,"Please enter food item quantity", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(description)) {
            Toast.makeText(this,"Please enter food item description", Toast.LENGTH_LONG).show();
        }
        else {
            storeFoodDonationInfo();
            
        }


    }
    //Store food item image into Firebase Storage
    //Then store image URL into Firebase Database
    private void storeFoodDonationInfo() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MM-dd-yyyy,HH-mm-ss-z");
        saveCurrentDate = currentdate.format(calendar.getTime());

        itemKey = saveCurrentDate;

        StorageReference filepath = itemImagesRef.child(imageUri.getLastPathSegment() + itemKey + ".jpg");

        final UploadTask uploadTask = filepath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FoodDonationActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(FoodDonationActivity.this, "Image uploaded Successfully ", Toast.LENGTH_LONG).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(FoodDonationActivity.this, "Retrieved item image Url", Toast.LENGTH_LONG).show();
                            saveFoodItemToDatabase();
                        }

                    }
                });
            }
        });

    }

    //Store item name, quantity, description, and image Url into Firebase Database
    //HashMap with String keys of itemKeys, and Object values of FoodDonationItems
    private void saveFoodItemToDatabase() {
        FoodDonationItem foodDonationItem = new FoodDonationItem(saveCurrentDate,name,quantity,description,downloadImageUrl);
        HashMap<String, FoodDonationItem> foodItemsMap = new HashMap<>();
        foodItemsMap.put(itemKey,foodDonationItem);
        FirebaseDatabase.getInstance().getReference("FoodDonationItem")
                .child("FoodDonationItems")
                .push()
                .setValue(foodItemsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FoodDonationActivity.this, "Food Donation has successfully submited!", Toast.LENGTH_LONG ).show();
                }
                else {
                    Toast.makeText(FoodDonationActivity.this, "Error: " + task.getException().toString(), Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data != null) {
            imageUri = data.getData();
            itemImage.setImageURI(imageUri);
        }
    }
}
