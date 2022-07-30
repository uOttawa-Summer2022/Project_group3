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
import java.util.List;

public class StudentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    TextView welcomeMsg, searchOrMy, courseSelectTxt;
    ArrayAdapter<String> allCourseAdapter;
    ListView courseListView;
    Button searchByNBtn2, logOut2, unenrollBtn, enrolBtn, editCourseBtn, viewAll2, myCousrseBtn ;
    List<String> courseStrList, myCourseStrList;
    List<Course> courseList, myCourseList;
    EditText  course_Name2;
    static Course course;
    CourseDb cdb;
    AccountDB adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg1);
        courseSelectTxt = (TextView) findViewById(R.id.courseSelectTxt);
        searchOrMy = (TextView) findViewById(R.id.searchOrMy);


        course_Name2 = (EditText) findViewById(R.id.course_Name2);

        courseListView = (ListView) findViewById(R.id.course_listView);

        viewAll2 = (Button) findViewById(R.id.viewAll2) ;
        searchByNBtn2 = (Button) findViewById(R.id.searchByNBtn2);

        logOut2 = (Button) findViewById(R.id.logOut2);

        cdb = new CourseDb(this);

        adb = new AccountDB(this);


        final Intent[] intent1 = {getIntent()};

        //welcome msg
        String fName = intent1[0].getStringExtra("firstName");
        String uName = intent1[0].getStringExtra("userName");
        String role = intent1[0].getStringExtra("role");

        courseStrList = cdb.findAllCourses();
        courseList = new ArrayList<>();
        myCourseStrList = adb.searchS_CourseList(uName);
        myCourseList = new ArrayList<>();

        String message = "Welcome " + fName + "/" + uName + "! You are logged in as " + role;

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg1);
        welcomeMsg.setText(role+": "+uName);

        for(String courseStr:courseStrList){
            String cCode = courseStr.split(":")[1];
            Course tempCourse =cdb.searchCourse(cCode,false);
            if((tempCourse != null)){
                courseList.add(tempCourse);
            }
        }

        for(String courseStr:myCourseStrList){
            String cCode = courseStr.split(":")[1];
            Course tempCourse =cdb.searchCourse(cCode,false);
            if((tempCourse != null)){
                myCourseList.add(tempCourse);
            }
        }



        //search by course name
        searchByNBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cName = course_Name2.getText().toString().trim();
                if(cName.equals("")){
                    Toast.makeText(StudentActivity.this, "Empty Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> tempList = new ArrayList<>();
                for(String temp:courseStrList){
                    if(temp.contains(cName)){
                        tempList.add(temp);
                    }
                }
                viewCourses(tempList);

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


                viewCourses(courseStrList);


            }
        });




    }

    public boolean printCourse(){
        if(course == null){
            Toast.makeText(StudentActivity.this, "Search Course fail", Toast.LENGTH_SHORT).show();
            return false;
        }
        courseSelectTxt.setText("Course Code: "+course.getCode());



        return true;
    }

    private void viewCourses(List<String> list) {
        allCourseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        courseListView.setAdapter(allCourseAdapter);

        //set click function to view the course detail
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(StudentActivity.this, "qwerty", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String code = courseStrList.get(position).split(":")[1];
        course = cdb.searchCourse(code, false);
        printCourse();
        Toast.makeText(this, courseStrList.get(position), Toast.LENGTH_SHORT).show();


    }
}