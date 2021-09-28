package com.example.communityeats;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button register;
    private EditText emailRegister;
    private EditText passwordRegister;
      protected void onCreate(Bundle savedInstanceState)
      {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_register);
          mAuth = FirebaseAuth.getInstance();

          register = (Button) findViewById(R.id.btnRegister);
          register.setOnClickListener(this);
          emailRegister = (EditText) findViewById(R.id.input_email_registration);
          passwordRegister = (EditText) findViewById(R.id.input_password_registration);
      }

    @Override
    public void onClick(View v) {
          registerUser();
    }
    private void registerUser()
    {
        String email = emailRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User u = new User(email,password);

                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this,"User has been registered successfully!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this,"Failed to register User!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this,"Failed to register!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
