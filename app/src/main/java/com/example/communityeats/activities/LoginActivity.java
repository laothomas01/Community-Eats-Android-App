package com.example.communityeats.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.communityeats.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity implements View.OnClickListener {


    private TextView signup;
    private FirebaseAuth mAuth;
    private Button login;

    //private TextView signUp;
    private EditText editTextEmail;
    private EditText editTextPassword;

    public static String loginToken;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.signIn);
        login.setOnClickListener(this);

        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        //==========================================================================================
        // Remember Me Features

        CheckBox remember ;

        remember = findViewById(R.id.rememberMe) ;

        SharedPreferences preferences = getSharedPreferences ("checkbox", MODE_PRIVATE) ;
        String checkbox = preferences.getString("remember", "") ;

        if (checkbox.equals("true"))
        {
            Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class) ;
            startActivity(intent);
        }

        else if (checkbox.equals("false"))
        {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show () ;
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                                    if (compoundButton.isChecked()) {
                                                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = preferences.edit();

                                                        editor.putString("remember", "true");

                                                        editor.apply();

                                                        Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                                                    } else if (!compoundButton.isChecked()) {
                                                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = preferences.edit();

                                                        editor.putString("remember", "false");

                                                        editor.apply();

                                                        Toast.makeText(LoginActivity.this, "Unhecked", Toast.LENGTH_SHORT).show();
                                                    }

                                                }

                                            }) ;

        // End Of Remember Me Features
        //==========================================================================================

    }

    @Override
    public void onClick(View v) {
        
        if (v.getId() == R.id.signup) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
        if (v.getId() == R.id.signIn) {
            userLogin();
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                    loginToken = task.getResult().getUser().getEmail();

                    startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
