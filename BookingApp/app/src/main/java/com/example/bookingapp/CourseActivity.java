package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    EditText sir;
    EditText courseCapacity, courseDescription, courseSessionDay, startTimeHour, endTimeHour, startTimeMinute, endTimeMinute;
    Button goBackInstructorActivity, deleteSession, addSession, logOut, editCapAndDes;
    com.example.bookingapp.CourseDb cdb;
    Course course;
    ArrayList<Session> sessionsList;
    com.example.bookingapp.AccountDB adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        courseCapacity = (EditText) findViewById(R.id.courseCapacity);
        courseDescription = (EditText)findViewById(R.id.courseDescription);

        courseSessionDay = (EditText) findViewById(R.id.courseSessionDay);
        startTimeHour = (EditText) findViewById(R.id.startTimeHour);
        startTimeMinute = (EditText) findViewById(R.id.startTimeMinute);
        endTimeHour = (EditText) findViewById(R.id.endTimeHour);
        endTimeMinute = (EditText) findViewById(R.id.endTimeMinute);

        goBackInstructorActivity = findViewById(R.id.button_goBackInstructorActivity);


        editCapAndDes = findViewById(R.id.button_EditCapAndDes);
        addSession = findViewById(R.id.button_addSession);
        deleteSession = findViewById(R.id.button_deleteSession);

        logOut = (Button) findViewById(R.id.logOut);

        cdb = new CourseDb(this);
        final Intent[] intent = {getIntent()};
        String userName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");

        course = InstructorActivity.course;
        sessionsList = course.getSessionList();

        deleteSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(getApplicationContext(), SessionActivity.class);
                intent[0].putExtra("userName", userName);
                intent[0].putExtra("role", role);
                startActivity(intent[0]);
            }
        });




        editCapAndDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseDescription1 = courseDescription.getText().toString();
                String courseCapacity1 = courseCapacity.getText().toString();

                if (!(courseCapacity1.matches("[0-9]+") && (Integer.parseInt(courseCapacity1) > 0))) {
                    Toast.makeText(CourseActivity.this, " Capacity must be Integer positive ", Toast.LENGTH_SHORT).show();
                } else if (courseCapacity1.isEmpty() || courseDescription1.isEmpty()) {
                    Toast.makeText(CourseActivity.this, " FILL THE DESCRIPTION AND CAPACITY ", Toast.LENGTH_SHORT).show();

                } else {

                    boolean FCap = cdb.editCrsCapacity(course.getCode(), Integer.parseInt(courseCapacity1));
                    boolean FDes = cdb.editCrsDescription(course.getCode(), courseDescription1);

                    if (FDes && FCap) {
                        Toast.makeText(CourseActivity.this, " added successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CourseActivity.this, "add unsuccessful", Toast.LENGTH_SHORT).show();
                    }

                }

            }


        });


        addSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = courseSessionDay.getText().toString();
                String sH = startTimeHour.getText().toString();
                String sM = startTimeMinute.getText().toString();
                String eH = endTimeHour.getText().toString();
                String eM = endTimeMinute.getText().toString();


                if (day.isEmpty() || sH.isEmpty() || sM.isEmpty() || eH.isEmpty() || eM.isEmpty()) {
                    Toast.makeText(CourseActivity.this, "fill all fields ", Toast.LENGTH_SHORT).show();
                } else if (stringToDays(day) == null) {
                    Toast.makeText(CourseActivity.this, "Please enter a valid day", Toast.LENGTH_SHORT).show();
                } else {
                    int i = Integer.parseInt(sH) - Integer.parseInt(eH);
                    if (i > 0){
                        Toast.makeText(CourseActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
                    } else if (i == 0 && Integer.parseInt(sM) - Integer.parseInt(eM) > 0){
                        Toast.makeText(CourseActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
                    } else {
                        Days tempDays = stringToDays(day);
                        int tempInt = tempDays.ordinal();
                        int[] tempA = course.getSessionIndex();
                        Session session = new Session(Integer.parseInt(sH), Integer.parseInt(sM), Integer.parseInt(eH), Integer.parseInt(eM), tempDays);


                        for(Session tempSession:sessionsList){
                            if(tempSession.isOverlap(session)){
                                Toast.makeText(CourseActivity.this, "Session is overlapping", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }

                        course.getSessionList().add(session);

                        Collections.sort(course.getSessionList());
                        int a,b;
                        a = tempA[tempInt];
                        b = tempA[tempInt+1];

                        List<Session> tempList = course.getSessionList().subList(a,b+1);
                        boolean addSession = cdb.overwriteSession(course.getCode(), stringToDays(day), tempList.toString());

                        if (addSession) {
                            Toast.makeText(CourseActivity.this, "Session Add Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CourseActivity.this, "Session Add Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }
        });

        goBackInstructorActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(getApplicationContext(), InstructorActivity.class);
                intent[0].putExtra("userName", userName);
                intent[0].putExtra("role", role);
                startActivity(intent[0]);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent[0]);
            }
        });


    }

    public static Days stringToDays(String str) {
        switch (str) {
            case "SU":
                return Days.Sunday;
            case "MO":
                return Days.Monday;
            case "TU":
                return Days.Tuesday;
            case "WE":
                return Days.Wednesday;
            case "TH":
                return Days.Thursday;
            case "FR":
                return Days.Friday;
            case "SA":
                return Days.Saturday;
            default:
                return null;
        }
    }



}