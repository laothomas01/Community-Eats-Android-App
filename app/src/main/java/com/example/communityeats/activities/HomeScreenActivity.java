package com.example.communityeats.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communityeats.MyAdapter;
import com.example.communityeats.model.FoodDonationItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.example.communityeats.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {
    private DatabaseReference FoodItemsRef;
    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<FoodDonationItem> list;
    ArrayList<String> mFoodKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        FoodItemsRef = FirebaseDatabase.getInstance().getReference("FoodDonationItem");

        recyclerView = findViewById(R.id.market_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String uid = FoodItemsRef.getKey();

        // FoodItemsRef.child(uid).get().addOnCompleteListener()

 // change here
    // FoodItemsRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
    //         @Override
    //         public void onComplete(@NonNull Task<DataSnapshot> task) {
    //             //if you successfully got the task
    //             if (task.isSuccessful()) {
    //                 //if the food donation item exists
    //                 if (task.getResult().exists()) {
    //                     //toast
    //                     Toast.makeText(HomeScreenActivity.this, "Successful Read", Toast.LENGTH_SHORT).show();
    //                     //create a snapshot of that data
    //                     DataSnapshot ds = task.getResult();

    //                     //get the email from the user node.
    //                     // String email = String.valueOf(ds.child("email").getValue());
    //                     // String username = String.valueOf(ds.child("username").getValue());
    //                     // String address = String.valueOf(ds.child("address").getValue());

    //                     String imageUrl = String.valueOf(ds.child("foodImageUrl").getValue());
    //                     // String imageUrl = String.valueOf(ds.child("imageURL").getValue());


    //                     // emailTxt.setText(email);
    //                     // usernameTxt.setText(username);
    //                     // addressTxt.setText(address);


    //                     Glide.with(HomeScreenActivity.this).load(imageUrl).into(profilePic);

    //                 } else {
    //                     // Toast.makeText(ViewUserProfileActivity.this, "User Not exist", Toast.LENGTH_SHORT).show();
    //                 }
    //             } else {
    //                 // Toast.makeText(ViewUserProfileActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
    //             }

    //         }


    //     });

         list = new ArrayList<>();
         myAdapter = new MyAdapter(this, list);
<<<<<<< Updated upstream
         mFoodKey = new ArrayList<>();
         myAdapter = new MyAdapter(this, list, this);
         recyclerView.setAdapter(myAdapter);

         FoodItemsRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                     FoodDonationItem foodItem = dataSnapshot.getValue(FoodDonationItem.class);
                     System.out.println("ID VALUE: " + dataSnapshot.child("recipientID").getValue());
                     list.add(foodItem);

                     String uid = dataSnapshot.getKey();
                     System.out.println("UID: " + uid);
                     if (uid!=null)  {
                         mFoodKey.add(uid);
                     }

                     if(!dataSnapshot.child("recipientID").getValue().equals("") ) {
                         list.remove(foodItem);
                         mFoodKey.remove(uid);
                     }

                 }
                 myAdapter.notifyDataSetChanged();
                 System.out.println("Food Donation Item Added");
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(20);
        recyclerView.addItemDecoration(dividerItemDecoration);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
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
                        startActivity(new Intent(HomeScreenActivity.this, ViewUserProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });



    @Override
    public void onItemClick(MyAdapter.FoodItemViewHolder holder, int position) {
        FoodDonationItem foodItem = list.get(position);
        Intent intent = new Intent(this, ViewFoodDonation.class);
        intent.putExtra("foodName", foodItem.foodName);
        intent.putExtra("foodDate", foodItem.date);
        intent.putExtra("description", foodItem.foodDescription);
        intent.putExtra("quantity", foodItem.foodQuantity);
        intent.putExtra("foodImage", foodItem.getFoodImageUrl());

        String foodKey = mFoodKey.get(holder.getBindingAdapterPosition());
        System.out.println("FOOD KEY:" + foodKey);
        intent.putExtra("FoodKey", foodKey);

        startActivity(intent);
    }

}

