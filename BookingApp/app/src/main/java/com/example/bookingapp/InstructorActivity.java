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

public class InstructorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView welcomeMsg, CourseTxt, instructorTxt, capacityTxt;
    EditText course_Code, course_Name;
    Button searchByNBtn, searchByCBtn, logOut, unAssignBtn, assignBtn, editCourseBtn, viewAll, descript, viewSession;
    ArrayList<String> courseList;
    static ArrayList<ArrayList<Session>> sessionList;
    ListView courseListView;
    ArrayAdapter<String> allCourseAdapter;
    static Course course;

    CourseDb cdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        instructorTxt = (TextView) findViewById(R.id.instructorTxt);
        capacityTxt= (TextView) findViewById(R.id.capacityTxt);
        CourseTxt = ( TextView) findViewById(R.id.CourseTxt);

        course_Code = (EditText) findViewById(R.id.course_Code);
        course_Name = (EditText) findViewById(R.id.course_Name);

        searchByNBtn = (Button) findViewById(R.id.searchByNBtn);
        searchByCBtn = (Button) findViewById(R.id.searchByCBtn);
        logOut = (Button) findViewById(R.id.logOut);
        unAssignBtn = (Button) findViewById(R.id.unAssignBtn);
        assignBtn = (Button) findViewById(R.id.assignBtn);
        editCourseBtn = (Button) findViewById(R.id.editCourseBtn);
        viewAll = (Button) findViewById(R.id.viewAll) ;
        descript = (Button) findViewById(R.id.description);
        viewSession = (Button) findViewById(R.id.viewSessionBtn);

        courseListView = (ListView) findViewById(R.id.course_listView);



        cdb = new CourseDb(this);

        courseList = cdb.findAllCourses();

        final Intent[] intent = {getIntent()};

        
        String userName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");

        welcomeMsg.setText(role+" "+userName);
        if(course != null){
            course = cdb.searchCourse(course.getCode(), false);
        }
        CourseTxt.setText("Course: "+ course.getName()+" "+course.getCode());
        instructorTxt.setText("Instructor: " + course.getInstructor());
        capacityTxt.setText("Capacity: "+ course.getCapacity());

        searchByCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course_Name.getText().toString().trim().equals("")){
                    Toast.makeText(InstructorActivity.this, "Empty Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                course = cdb.searchCourse(course_Code.getText().toString(),false);
                printCourse();

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
                printCourse();

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


                printCourse();

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


                printCourse();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent[0]);
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewCourses(courseList);


            }
        });

        editCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course == null){
                    Toast.makeText(InstructorActivity.this, "Search Course fail", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent[0] = new Intent(getApplicationContext(), CourseActivity.class);
                intent[0].putExtra("userName", userName);
                intent[0].putExtra("role", role);
                startActivity(intent[0]);

            }
        });

        descript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course == null){
                    Toast.makeText(InstructorActivity.this, "Search Course fail", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent[0] = new Intent(getApplicationContext(), WelcomeActivity.class);
                intent[0].putExtra("Des", course.getDescription());
                intent[0].putExtra("userName", userName);
                intent[0].putExtra("role", "1"+role);
                startActivity(intent[0]);
            }
        });

        courseListView.setOnItemClickListener(this);

        viewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course == null){
                    Toast.makeText(InstructorActivity.this, "Search Course fail", Toast.LENGTH_SHORT).show();
                    return;
                }
                sessionList = course.getSessionList();
                intent[0] = new Intent(getApplicationContext(), SessionActivity.class);
                startActivity(intent[0]);
            }
        });
    }

    public boolean printCourse(){
        if(course == null){
            Toast.makeText(InstructorActivity.this, "Search Course fail", Toast.LENGTH_SHORT).show();
            return false;
        }
        CourseTxt.setText("Course: "+ course.getName()+" "+course.getCode());
        instructorTxt.setText("Instructor: " + course.getInstructor());
        capacityTxt.setText("Capacity: "+ course.getCapacity());

        return true;
    }

    private void viewCourses(ArrayList<String> list) {
        allCourseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        courseListView.setAdapter(allCourseAdapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String code =courseList.get(position).split(":")[1];
        course = cdb.searchCourse(code, false);

        Toast.makeText(this, courseList.get(position), Toast.LENGTH_SHORT).show();
        printCourse();

    }
}