package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeMsg;
    Button screenBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Intent[] intent = {getIntent()};

        String fName = intent[0].getStringExtra("firstName");
        String uName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");
        String message;

        if(role.charAt(0) == '1'){

            message = intent[0].getStringExtra("Des");
        }else {
            message = "Welcome " + fName + "/" + uName + "!\nYou are logged in as " + role;
        }


        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        welcomeMsg.setText(message);



        screenBtn = (Button) findViewById(R.id.screenBtn);

        screenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(role.equals("admin")){
                    intent[0] = new Intent(getApplicationContext(), AdminActivity.class);
                }else if(role.equals("Instructor")){
                    intent[0] = new Intent(getApplicationContext(), InstructorActivity.class);
                }else if(role.equals("Student")){
                    intent[0] = new Intent(getApplicationContext(), StudentActivity.class);
                }else {
                    intent[0] = new Intent(getApplicationContext(), InstructorActivity.class);
                }
                intent[0].putExtra("userName", uName);
                intent[0].putExtra("role", role);
                startActivity(intent[0]);
            }
        });



    }
}