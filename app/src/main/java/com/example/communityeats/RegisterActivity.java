package com.example.communityeats;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class RegisterActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button register;
    private EditText nameRegister;
    private EditText passwordRegister;
      protected void onCreate(Bundle savedInstanceState)
      {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_register);
          mAuth = FirebaseAuth.getInstance();

          register = (Button) findViewById(R.id.btnRegister);
          register.setOnClickListener(this);
          nameRegister = (EditText) findViewById(R.id.input_name_register);
          passwordRegister = (EditText) findViewById(R.id.input_password_registration);
      }

    @Override
    public void onClick(View v) {
          registerUser();
    }
    private void registerUser()
    {
        String username = nameRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword("",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User u = new User(username,password);
                    
                }
            }
        });

    }
}
