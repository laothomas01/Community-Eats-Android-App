package com.example.communityeats.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;

import com.example.communityeats.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    /*
    Initialize our XML components
     */
    private ImageView profile_pic;
    private Button update;

    //request code for photo gallery access
    private static final int GalleryPick = 1;

    private Uri imageUri;
    //path of the image to be uploaded
    private String downloadImageUrl;

    //firebase auth
    private static final String USER = "User";
    private FirebaseUser user;
    //access to firebase storage
    StorageReference storage;
    private DatabaseReference ref;
    ///user Id from firebase
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        profile_pic = findViewById(R.id.upload_profile_image);
        update = findViewById(R.id.Submit);

        profile_pic.setOnClickListener(this);
        update.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        //get the ID of our current user because we do not have usernames.
        uid = user.getUid();
        //reference to get our User node
        ref = FirebaseDatabase.getInstance().getReference(USER);
        //reference the children of the user node.
        storage = FirebaseStorage.getInstance().getReference().child("profile_picture");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*
            will do 2 things:
                -set users profile picture to desired picture
             */
            case R.id.Submit:
                storeProfilePictureInfo();

            case R.id.upload_profile_image:
                openGallery();

        }


    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    /*
    Note: test this later.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profile_pic.setImageURI(imageUri);
        }
    }

    private void storeProfilePictureInfo() {
        StorageReference filepath = storage.child(imageUri.getLastPathSegment() + ".jpg");
        final UploadTask uploadTask = filepath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateProfileActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UpdateProfileActivity.this, "Image uploaded Successfully ", Toast.LENGTH_LONG).show();


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
                            Toast.makeText(UpdateProfileActivity.this, "Retrieved item image Url", Toast.LENGTH_LONG).show();

                            ref.child(uid).child("imageURL").setValue(downloadImageUrl);

                        }
                    }
                });
            }


        });


    }

}

