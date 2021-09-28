package com.example.communityeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        private Button register;
        private TextView nameRegister;
        private TextView passwordRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);
        nameRegister = (TextView) findViewById(R.id.input_name_register);
        passwordRegister = (TextView) findViewById(R.id.input_password_registration);
    }

    @Override
    public void onClick(View v) {

    }
}