package com.example.bookingapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText firstname, lastname,  username, password;
    RadioButton role_instructor,role_student;
    Button signup, signin;
    AccountDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        role_instructor = (RadioButton) findViewById(R.id.role_instructor);
        role_student = (RadioButton) findViewById(R.id.role_student);

        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);

        db = new AccountDB(this);

        clickActions();

    }

    private void clickActions(){

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = firstname.getText().toString();
                String ln = lastname.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(role_instructor.isChecked()) {
                    String rlI = role_instructor.getText().toString();

                    if (fn.equals("") || ln.equals("") || user.equals("") || pass.equals("") || rlI.equals("")){
                        Toast.makeText(MainActivity.this, "Please enter all the missing fields ...", Toast.LENGTH_SHORT).show();
                    } else{
                        Boolean validUser = db.checkUsername(user);
                        if(validUser==false){
                            Boolean insert = db.addAccount(fn, ln, user, pass, rlI);
                            if(insert==true){
                                Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),InstructorActivity.class);
                                intent.putExtra("firstName", fn);
                                intent.putExtra("userName", user);
                                intent.putExtra("role", rlI);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registration failed ...", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(MainActivity.this, "User already exists! Please sign in ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else{
                    String rlS = role_student.getText().toString();

                    if (fn.equals("") || ln.equals("") || user.equals("") || pass.equals("") || rlS.equals("")){
                        Toast.makeText(MainActivity.this, "Please enter all the missing fields ...", Toast.LENGTH_SHORT).show();
                    } else{
                        Boolean validUser = db.checkUsername(user);
                        if(validUser==false){
                            Boolean insert = db.addAccount(fn, ln, user, pass, rlS);
                            if(insert==true){
                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                                intent.putExtra("firstName", fn);
                                intent.putExtra("userName", user);
                                intent.putExtra("role", rlS);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(MainActivity.this, "User already exists! Please sign in ...", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });



    }


}