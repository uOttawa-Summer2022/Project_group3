package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity {

    TextView welcomeMsg, instructorTxt, capacityTxt;
    EditText course_Code, course_Name;
    Button searchByNBtn, searchByCBtn, logOut, unAssignBtn, assignBtn, editCourseBtn;

    Course course;

    CourseDb cdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        instructorTxt = (TextView) findViewById(R.id.instructorTxt);
        capacityTxt= (TextView) findViewById(R.id.capacityTxt);

        course_Code = (EditText) findViewById(R.id.course_Code);
        course_Name = (EditText) findViewById(R.id.course_Name);

        searchByNBtn = (Button) findViewById(R.id.searchByNBtn);
        searchByCBtn = (Button) findViewById(R.id.searchByCBtn);
        logOut = (Button) findViewById(R.id.logOut);
        unAssignBtn = (Button) findViewById(R.id.unAssignBtn);
        assignBtn = (Button) findViewById(R.id.assignBtn);
        editCourseBtn = (Button) findViewById(R.id.editCourseBtn);

        cdb = new CourseDb(this);

        final Intent[] intent = {getIntent()};

        
        String userName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");



        searchByCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course_Name.getText().toString().trim().equals("")){
                    Toast.makeText(InstructorActivity.this, "Empty Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                course = cdb.searchCourse(course_Code.getText().toString(),false);
                emptyCourse();

            }
        });

        searchByNBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course_Name.getText().toString().trim().equals("")){
                    Toast.makeText(InstructorActivity.this, "Empty Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                course = cdb.searchCourse(course_Name.getText().toString(),true);
                emptyCourse();

            }
        });
        

        assignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course!=null){
                    if(course.getInstructor() == null||course.getInstructor().equals("null")){
                        cdb.EditCourseInstructor(course.getCode(),userName);
                        course = cdb.searchCourse(course.getCode(),false);
                        Toast.makeText(InstructorActivity.this, "Assign Success", Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(InstructorActivity.this, "Assign Fail", Toast.LENGTH_SHORT).show();
                    }
                }


                emptyCourse();

            }
        });

        unAssignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course!=null){
                    if(course.getInstructor() != null && course.getInstructor().equals(userName)){
                        cdb.EditCourseInstructor(course.getCode(),"null");
                        course = cdb.searchCourse(course.getCode(),false);
                        Toast.makeText(InstructorActivity.this, "Un-assign Success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InstructorActivity.this, "Un-assign Fail", Toast.LENGTH_SHORT).show();
                    }
                }


                emptyCourse();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent[0]);
            }
        });

        editCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyCourse();
                intent[0] = new Intent(getApplicationContext(), CourseActivity.class);
                intent[0].putExtra("userName", userName);
                intent[0].putExtra("role", role);
                startActivity(intent[0]);
            }
        });
    }

    public boolean emptyCourse(){
        if(course == null){
            Toast.makeText(InstructorActivity.this, "Empty Course!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        instructorTxt.setText("Instructor: " + course.getInstructor()+course);
        capacityTxt.setText("Capacity: "+ course.getCapacity());
        return true;
    }








}