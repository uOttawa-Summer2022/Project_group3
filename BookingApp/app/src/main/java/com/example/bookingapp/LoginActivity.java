package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnlogin;
    AccountDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        db = new AccountDB(this);

        clickActions();

    }

    private void clickActions(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                String firstname = db.getFirstNameWithUser(user);
                String role = db.getRoleWithUser(user);

                if (user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter all the missing fields ...", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean validUserAndPass = db.checkUsernameAndPassword(user, pass);
                    if (validUserAndPass == true){
                        if (role.equals("admin")){
                            Toast.makeText(LoginActivity.this, "Successfully Signed In!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                            startActivity(intent);
                        }
                        else if (role.equals("Instructor")){
                            Toast.makeText(LoginActivity.this, "Successfully Signed In!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), InstructorActivity.class);
                            intent.putExtra("firstName", firstname);
                            intent.putExtra("userName", user);
                            intent.putExtra("role", role);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                            intent.putExtra("firstName", firstname);
                            intent.putExtra("userName", user);
                            intent.putExtra("role", role);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials, please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}