package com.example.communityeats.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.communityeats.R;
import com.example.communityeats.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button register;
    private Button haveAccount;
    private EditText email;
    private EditText password;
    private EditText username;
    private EditText address;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);
        haveAccount = (Button) findViewById(R.id.btnHaveAccount);
        haveAccount.setOnClickListener(this);
        email = (EditText) findViewById(R.id.input_email_registration);
        password = (EditText) findViewById(R.id.input_password_registration);
        username = (EditText) findViewById(R.id.input_username_register);
        address = (EditText) findViewById(R.id.input_address_registration);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            registerUser();
        }
        if (v.getId() == R.id.btnHaveAccount) {
            startActivity(new Intent(this, LoginActivity.class));
        }
//        switch (v.getId()) {
//            case R.id.btnRegister:
//                registerUser();
//
//            case R.id.btnHaveAccount:
//                startActivity(new Intent(this, LoginActivity.class));
//        }

    }

    private void registerUser() {
        String emailRegister = email.getText().toString().trim();
        String passwordRegister = password.getText().toString().trim();
        String usernameRegister = username.getText().toString().trim();
        String addressRegister = address.getText().toString().trim();
        //public User(String email, String password,String username, String address
        if (emailRegister.isEmpty() && passwordRegister.isEmpty() && usernameRegister.isEmpty() && addressRegister.isEmpty()) {
            email.setError("Enter an email!");
            password.setError("Enter a password!");
            username.setError("Enter a username!");
            address.setError("Enter an address!");
            Toast.makeText(RegisterActivity.this, "Missing Information!", Toast.LENGTH_LONG).show();
            return;
        }
        if (emailRegister.isEmpty()) {
            email.setError("Enter an email!");
            return;
        }

        if (passwordRegister.isEmpty()) {
            password.setError("Enter a password!");
            return;
        }

        if (usernameRegister.isEmpty()) {
            username.setError("Enter a username!");
            return;
        }

        if (addressRegister.isEmpty()) {
            address.setError("Enter an address!");
            return;
        }

        User u = new User(emailRegister, passwordRegister, usernameRegister, addressRegister, "");
        {

            mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseDatabase.getInstance().getReference("User")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                //find a way to not store the password
                                .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else {

                                    Toast.makeText(RegisterActivity.this, "Failed to register User!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {

                        Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();

                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                        switch (errorCode) {

                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(RegisterActivity.this, "Email address is invalid", Toast.LENGTH_LONG).show();
                                email.setError("Email must be in valid email address format!");
                                email.requestFocus();
                                break;

                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                Toast.makeText(RegisterActivity.this, "Email address is invalid", Toast.LENGTH_LONG).show();
                                email.setError("Email address is already in use!");
                                email.requestFocus();
                                break;

                            case "ERROR_WEAK_PASSWORD":
                                Toast.makeText(RegisterActivity.this, "Password is invalid", Toast.LENGTH_LONG).show();
                                password.setError("Password must be at least 6 characters!");
                                password.requestFocus();
                                break;
                        }

                    }

                }
            });


        }

    }
}
