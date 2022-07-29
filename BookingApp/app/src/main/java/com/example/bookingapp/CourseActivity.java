package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CourseActivity extends AppCompatActivity {
    EditText sir;
    EditText courseCapacity, courseDescription, courseSessionDay, startTimeHour, endTimeHour, startTimeMinute, endTimeMinute;
    Button goBackInstructorActivity, deleteSession, addSession, logOut, editCapAndDes, addCapAndDes;
    com.example.bookingapp.CourseDb cdb;
    Course course;
    com.example.bookingapp.AccountDB adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        courseCapacity = (EditText) findViewById(R.id.courseCapacity);
        courseDescription = findViewById(R.id.courseDescription);
        courseSessionDay = findViewById(R.id.courseSessionDay);
        startTimeHour = findViewById(R.id.startTimeHour);
        startTimeMinute = findViewById(R.id.startTimeMinute);
        endTimeHour = findViewById(R.id.endTimeHour);
        endTimeMinute = findViewById(R.id.endTimeMinute);

        goBackInstructorActivity = findViewById(R.id.button_goBackInstructorActivity);


        editCapAndDes = findViewById(R.id.button_EditCapAndDes);
        addSession = findViewById(R.id.button_addSession);
        deleteSession = findViewById(R.id.button_deleteSession);
        cdb = new CourseDb(this);
        final Intent[] intent = {getIntent()};
        String userName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");
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

                    boolean FCap = cdb.editCrsCapacity(InstructorActivity.course.getCode(), Integer.parseInt(courseCapacity1));
                    boolean FDes = cdb.editCrsDescription(InstructorActivity.course.getCode(), courseDescription1);
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
                /*String day = courseSessionDay.getText().toString();
                String sH = startTimeHour.getText().toString();
                String sM = startTimeMinute.getText().toString();
                String eH = endTimeHour.getText().toString();
                String eM = endTimeMinute.getText().toString();


                if (day.isEmpty() || sH.isEmpty() || sM.isEmpty() || eH.isEmpty() || eM.isEmpty()) {
                    Toast.makeText(CourseActivity.this, "fill all fields ", Toast.LENGTH_SHORT).show();

                } else if (!day.equals("SU") && !day.equals("MO") && !day.equals("TU") && !day.equals("WE") && !day.equals("TH") && !day.equals("FR") && !day.equals("SA")) {
                    Toast.makeText(CourseActivity.this, "enter a valid day", Toast.LENGTH_SHORT).show();

                } else {
                    Session session = new Session(Integer.parseInt(sH), Integer.parseInt(sM), Integer.parseInt(eH), Integer.parseInt(eM), Days.stringToDays(day));
                    boolean addSession = cdb.overwriteSession(InstructorActivity.course.getCode(), session);

                    if (addSession) {
                        Toast.makeText(CourseActivity.this, "Session add successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CourseActivity.this, "Session add unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }

                 */
            }
        });


    }



}