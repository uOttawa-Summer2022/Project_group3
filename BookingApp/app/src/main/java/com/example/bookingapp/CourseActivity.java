package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

        addCapAndDes = findViewById(R.id.button_AddCapAndDes);
        editCapAndDes = findViewById(R.id.button_EditCapAndDes);
        addSession = findViewById(R.id.button_addSession);
        deleteSession = findViewById(R.id.button_deleteSession);
        cdb = new CourseDb(this);


        addCapAndDes.setOnClickListener(new View.OnClickListener() {
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
                String day = courseSessionDay.getText().toString();
                String sH = startTimeHour.getText().toString();
                String sM = startTimeMinute.getText().toString();
                String eH = endTimeHour.getText().toString();
                String eM = endTimeMinute.getText().toString();


                if (day.isEmpty() || sH.isEmpty() || sM.isEmpty() || eH.isEmpty() || eM.isEmpty()) {
                    Toast.makeText(CourseActivity.this, "fill all fields ", Toast.LENGTH_SHORT).show();

                } else if (!day.equals("Monday") && !day.equals("Tuesday") && !day.equals("Wednesday") && !day.equals("Thursday") && !day.equals("Friday")) {
                    Toast.makeText(CourseActivity.this, "enter a valid day", Toast.LENGTH_SHORT).show();

                } else {
                    Session session = new Session(Integer.parseInt(sH), Integer.parseInt(sM), Integer.parseInt(eH), Integer.parseInt(eM), stringToDays(day));
                    boolean addSession = cdb.AddSession(InstructorActivity.course.getCode(), new Session[]{session});

                    if (addSession) {
                        Toast.makeText(CourseActivity.this, "Session add successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CourseActivity.this, "Session add unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    //EditTexts

    private Days stringToDays(String str) {
        switch (str) {
            case "Monday":
                return Days.Monday;
            case "Tuesday":
                return Days.Tuesday;
            case "Wednesday":
                return Days.Wednesday;
            case "Thursday":
                return Days.Thursday;
            default:
                return Days.Friday;
        }


    }
}