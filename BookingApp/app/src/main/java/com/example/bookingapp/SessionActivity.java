package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SessionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView sessionListView;
    Button GotoSearchBtn, addSessionBtn, delSessionBtn, refreshBtn, studentBtn;
    TextView sessionTxt, sessionInfoTxt, courseTitleTxt, descriptionTxt;
    ArrayList<Session> sessionList;
    ArrayList<String> studentList;
    ArrayAdapter<Session> sessionAdapter;
    ArrayAdapter<String> studentAdapter;
    CourseDb cdb;
    Course course;
    String sessionString;
    int position;
    boolean showSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        sessionListView = (ListView) findViewById(R.id.sessionListView);

        GotoSearchBtn = (Button) findViewById(R.id.GotoSearchBtn);
        addSessionBtn = (Button) findViewById(R.id.addSessionBtn);
        delSessionBtn = (Button) findViewById(R.id.delSessionBtn);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        studentBtn = (Button) findViewById(R.id.studentBtn);

        courseTitleTxt = (TextView) findViewById(R.id.courseTitleTxt);
        sessionTxt = (TextView) findViewById(R.id.sessionTxt);
        sessionInfoTxt = (TextView) findViewById(R.id.sessionInfoTxt);
        descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);

        showSession = true;







        final Intent[] intent = {getIntent()};


        String userName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");

        if(role.equals("Student")){
            course = StudentActivity.course;
        }else {
            course = InstructorActivity.course;
        }

        cdb = new CourseDb(this);

        sessionList = course.getSessionList();

        sessionListView.setOnItemClickListener(this);


        loadSessionListView();

        descriptionTxt.setText("Description: "+course.getDescription()+"\nCapacity: "+course.getCapacity());

        courseTitleTxt.setText(course.getName()+":"+course.getCode());

        addSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course.getInstructor() != null && course.getInstructor().equals(userName)){
                    Toast.makeText(SessionActivity.this, "Permitted", Toast.LENGTH_SHORT).show();
                    intent[0] = new Intent(getApplicationContext(), CourseActivity.class);
                    intent[0].putExtra("userName", userName);
                    intent[0].putExtra("role", role);
                    startActivity(intent[0]);
                }else{
                    Toast.makeText(SessionActivity.this, "Not-Permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(course.getInstructor() != null && course.getInstructor().equals(userName)){
                    if(sessionString != null){
                        Days tempDays = sessionList.get(position).getDay();
                        int tempInt = tempDays.ordinal();
                        int[] tempA = course.getSessionIndex();
                        sessionList.remove(position);
                        sessionString = null;
                        List<Session> tempList = sessionList.subList(tempA[tempInt],tempA[tempInt+1]-1);
                        cdb.overwriteSession(course.getCode(),tempDays, tempList.toString());
                        loadSessionListView();
                        Toast.makeText(SessionActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SessionActivity.this, "No Selected Session", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SessionActivity.this, "Not-Permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSessionListView();
                showSession = true;
            }
        });

        GotoSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(getApplicationContext(), InstructorActivity.class);
                intent[0].putExtra("userName", userName);
                intent[0].putExtra("role", role);
                startActivity(intent[0]);
            }
        });

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course.getInstructor() != null && course.getInstructor().equals(userName)){
                    loadStudentListView();
                    showSession = false;
                }else {
                    Toast.makeText(SessionActivity.this, "Not Permitted", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void loadSessionListView(){
        course = cdb.searchCourse(course.getCode(),false);
        sessionList = course.getSessionList();


        sessionAdapter = new ArrayAdapter<Session>(this, android.R.layout.simple_list_item_1,sessionList);
        sessionListView.setAdapter(sessionAdapter);
    }

    private void loadStudentListView(){
        course = cdb.searchCourse(course.getCode(),false);
        studentList = course.getStudentNameList();


        studentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,studentList);
        sessionListView.setAdapter(studentAdapter);
    }

    private boolean printSession(){
        if(sessionList == null){
            sessionInfoTxt.setText("Empty Session");
            return false;
        }else {
            sessionInfoTxt.setText(sessionString);
            return true;
        }



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(showSession) {
            sessionString = sessionList.get(position).toString();
            this.position = position;
            printSession();
        }

    }



}