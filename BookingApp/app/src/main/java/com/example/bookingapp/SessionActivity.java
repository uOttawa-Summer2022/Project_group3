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

public class SessionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView sessionListView;
    ArrayList<Session> sessionList;
    Button GotoSearchBtn, addSessionBtn, delSessionBtn;
    TextView sessionTxt, sessionInfoTxt;
    ArrayAdapter<Session> sessionAdapter;
    CourseDb cdb;
    Course course;
    String sessionString;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        sessionListView = (ListView) findViewById(R.id.sessionListView);

        GotoSearchBtn = (Button) findViewById(R.id.GotoSearchBtn);
        addSessionBtn = (Button) findViewById(R.id.addSessionBtn);
        delSessionBtn = (Button) findViewById(R.id.delSessionBtn);

        sessionTxt = (TextView) findViewById(R.id.sessionTxt);
        sessionInfoTxt = (TextView) findViewById(R.id.sessionInfoTxt);

        course = InstructorActivity.course;

        cdb = new CourseDb(this);

        sessionList = course.getSessionList();

        loadSessionListView();



        final Intent[] intent = {getIntent()};


        String userName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");



        sessionListView.setOnItemClickListener(this);

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




                    }else {
                        Toast.makeText(SessionActivity.this, "No Selected Session", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(SessionActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SessionActivity.this, "Not-Permitted", Toast.LENGTH_SHORT).show();
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

    private boolean printSession(){
        if(sessionList == null){
            Toast.makeText(this, "No Session In This Course", Toast.LENGTH_SHORT).show();
            return false;
        }

        sessionInfoTxt.setText(sessionString);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sessionString = sessionList.get(position).toString();
        this.position = position;
        printSession();


    }



}