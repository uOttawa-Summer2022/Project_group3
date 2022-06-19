package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InstructorActivity extends AppCompatActivity {

    TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        Intent intent = getIntent();

        String fName = intent.getStringExtra("firstName");
        String uName = intent.getStringExtra("userName");
        String role = intent.getStringExtra("role");

        String message = "Welcome " + fName + "/" + uName + "! You are logged in as " + role;

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        welcomeMsg.setText(message);



    }
}