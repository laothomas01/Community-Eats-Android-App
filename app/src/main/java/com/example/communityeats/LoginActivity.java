package com.example.communityeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {
    private TextView signup;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.signup:
                startActivity(new Intent(this,RegisterActivity.class));
        }
    }
}
