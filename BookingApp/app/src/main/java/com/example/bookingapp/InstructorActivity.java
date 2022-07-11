package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity {

    TextView welcomeMsg, instructorTxt, capacityTxt;
    EditText course_Code, course_Name;
    Button searchByNBtn, searchByCBtn, logOut, unAssignBtn, assignBtn, editCourseBtn;

    ArrayList<String> Course = new ArrayList<>();

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
        editCourseBtn = (Button) findViewById(R.id.assignBtn);

        cdb = new CourseDb(this);

        searchByCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instructorTxt.setText(cdb.searchCourseByC(course_Code.getText().toString()));
            }
        });

    }






}