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

    }
}