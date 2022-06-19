package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    EditText accountName, CourseName, CourseCode, newCourseName, newCourseCode;
    Button delAcc, delCourse, createCourse, editByName, editByCode, logOut;
    CourseDb cdb;
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

        cdb = new CourseDb(this);


        delAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accNameS = accountName.getText().toString();

            }
        });

        delCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CCodeS = CourseCode.getText().toString();
                cdb.deleteCourse(CCodeS);
            }
        });

        createCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CNameS = CourseName.getText().toString();
                String CCodeS = CourseCode.getText().toString();
                cdb.AddCourse(new Course(CNameS,CCodeS));
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

            }
        });
    }
    private void printToast(boolean editSucc){
        if(editSucc){
            Toast.makeText(AdminActivity.this, "Course Edit Successes", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(AdminActivity.this, "Course Edit Failed", Toast.LENGTH_SHORT).show();
        }
    }

}