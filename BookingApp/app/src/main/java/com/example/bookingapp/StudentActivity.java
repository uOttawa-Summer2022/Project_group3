package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    TextView welcomeMsg, studentTxt, capacityTxt;
    ArrayAdapter<String> allCourseAdapter;
    ListView courseListView;
    Button searchByNBtn2, searchByCBtn2, logOut2, unAssignBtn, assignBtn, editCourseBtn, viewAll2, descript, viewSession;
    ArrayList<String> courseList, searchCourseList;
    EditText course_Code2, course_Name2;
    static Course course;
    CourseDb cdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg1);

        courseListView = (ListView) findViewById(R.id.course_listView);
        viewAll2 = (Button) findViewById(R.id.viewAll2) ;

        course_Code2 = (EditText) findViewById(R.id.course_Code2);
        course_Name2 = (EditText) findViewById(R.id.course_Name2);

        searchByNBtn2 = (Button) findViewById(R.id.searchByNBtn2);
        searchByCBtn2 = (Button) findViewById(R.id.searchByCBtn2);
        logOut2 = (Button) findViewById(R.id.logOut2);

        cdb = new CourseDb(this);
        courseList = cdb.findAllCourses();

        final Intent[] intent1 = {getIntent()};
        String userName = intent1[0].getStringExtra("userName");
        String Role = intent1[0].getStringExtra("role");

        //welcome msg
        String fName = intent1[0].getStringExtra("firstName");
        String uName = intent1[0].getStringExtra("userName");
        String role = intent1[0].getStringExtra("role");

        String message = "Welcome " + fName + "/" + uName + "! You are logged in as " + role;

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg1);
        welcomeMsg.setText(role+": "+userName);

        //search by course code
        searchByCBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course_Name2.getText().toString().trim().equals("")){
                    Toast.makeText(StudentActivity.this, "Empty Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                course = cdb.searchCourse(course_Code2.getText().toString(),false);
                printCourse();

            }
        });

        //search by course name
        searchByNBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course_Name2.getText().toString().trim().equals("")){
                    Toast.makeText(StudentActivity.this, "Empty Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                course = cdb.searchCourse(course_Name2.getText().toString(),true);
                printCourse();

            }
        });


        //log out
        logOut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1[0] = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1[0]);
            }
        });

        //view all course
        viewAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewCourses(courseList);


            }
        });


    }

    public boolean printCourse(){
        if(course == null){
            Toast.makeText(StudentActivity.this, "Search Course fail", Toast.LENGTH_SHORT).show();
            return false;
        }
        studentTxt.setText("Student: " + course.getInstructor());
        capacityTxt.setText("Capacity: "+ course.getCapacity());

        return true;
    }

    private void viewCourses(ArrayList<String> list) {
        allCourseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        courseListView.setAdapter(allCourseAdapter);

        //set click function to view the course detail
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent detail = new Intent(getApplicationContext(), CourseDetailActivity.class);
                //detail.putExtra("name/code", allCourseAdapter.getItem(position));
                //startActivity(detail);


            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String code =courseList.get(position).split(":")[1];
        course = cdb.searchCourse(code, false);

        Toast.makeText(this, courseList.get(position), Toast.LENGTH_SHORT).show();
        printCourse();

    }
}