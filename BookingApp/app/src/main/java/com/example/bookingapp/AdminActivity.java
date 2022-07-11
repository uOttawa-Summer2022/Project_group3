package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    EditText accountName, CourseName, CourseCode, newCourseName, newCourseCode;
    Button delAcc, delCourse, createCourse, editByName, editByCode, logOut;
    com.example.bookingapp.CourseDb cdb;
    com.example.bookingapp.AccountDB adb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        accountName = findViewById(R.id.AccName);
        CourseName = findViewById(R.id.Course_Name);
        CourseCode = findViewById(R.id.Course_Code);
        newCourseName = findViewById(R.id.NewCourseName);
        newCourseCode = findViewById(R.id.NewCourseCode);

        editByName = findViewById(R.id.EditByName);
        editByCode = findViewById(R.id.EditByCode);
        delAcc = findViewById(R.id.DelAcc);
        delCourse = findViewById(R.id.DelCourse);
        createCourse = findViewById(R.id.CreateCourse);

        logOut = findViewById(R.id.LogOut);

        cdb = new com.example.bookingapp.CourseDb(this);
        adb = new com.example.bookingapp.AccountDB(this);

        delAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String accNameS = accountName.getText().toString();
                boolean result=adb.deleteAccount(accNameS);
                if(result){
                    Toast.makeText(AdminActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AdminActivity.this, "Delete unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CCodeS = CourseCode.getText().toString();

                boolean result=cdb.deleteCourse(CCodeS);
                if(result){
                    Toast.makeText(AdminActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AdminActivity.this, "Delete unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CNameS = CourseName.getText().toString();
                String CCodeS = CourseCode.getText().toString();
                boolean result =cdb.AddCourse(new com.example.bookingapp.Course(CCodeS,CNameS));
                if(result){
                    Toast.makeText(AdminActivity.this, "Add successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AdminActivity.this, "Add unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OCNameS = CourseName.getText().toString();
                String NCNameS = newCourseName.getText().toString();
                String NCCodeS = newCourseCode.getText().toString();
                printToast(cdb.EditCourse(OCNameS, true, NCNameS, NCCodeS));
            }
        });



        editByCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OCCodeS = CourseCode.getText().toString();
                String NCNameS = newCourseName.getText().toString();
                String NCCodeS = newCourseCode.getText().toString();
                printToast(cdb.EditCourse(OCCodeS, false, NCNameS, NCCodeS));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.bookingapp.MainActivity.class));

            }
        });
    }
    private void printToast(boolean editSucc){
        if(editSucc){
            Toast.makeText(AdminActivity.this, "Course Edit Successful", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(AdminActivity.this, "Course Edit Failed", Toast.LENGTH_SHORT).show();
        }
    }



}