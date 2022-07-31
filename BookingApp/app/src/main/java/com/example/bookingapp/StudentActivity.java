package com.example.bookingapp;

import android.content.Intent;
import android.graphics.Color;
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
    Button searchByNBtn2, logOut2, unenrollBtn, enrolBtn, searchByDBtn, viewAll2, myCousrseBtn ;

    ArrayList<String> courseStrList, myCourseStrList, currentC_StrList;
    ArrayList<Course> courseList, myCourseList, currentCList;

    EditText course_Name2, course_day;
    static Course course;
    CourseDb cdb;
    AccountDB adb;
    String fName, uName, role;
    int pos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg1);
        courseSelectTxt = (TextView) findViewById(R.id.courseSelectTxt);
        searchOrMy = (TextView) findViewById(R.id.searchOrMy);


        course_Name2 = (EditText) findViewById(R.id.course_Name2);
        course_day = (EditText) findViewById(R.id.course_day);

        courseListView = (ListView) findViewById(R.id.course_listView);

        searchByNBtn2 = (Button) findViewById(R.id.searchByNBtn2);
        searchByDBtn = (Button) findViewById(R.id.searchByDBtn);
        viewAll2 = (Button) findViewById(R.id.viewAll2) ;
        myCousrseBtn = (Button) findViewById(R.id.myCousrseBtn);
        enrolBtn = (Button) findViewById(R.id.enrolBtn);
        unenrollBtn = (Button) findViewById(R.id.unenrollBtn);


        logOut2 = (Button) findViewById(R.id.logOut2);



        cdb = new CourseDb(this);

        adb = new AccountDB(this);


        final Intent[] intent1 = {getIntent()};

        //welcome msg
        fName = intent1[0].getStringExtra("firstName").trim();
        uName = intent1[0].getStringExtra("userName").trim();
        role = intent1[0].getStringExtra("role").trim();

        courseStrList = cdb.findAllCourses();
        courseList = new ArrayList<>();
        myCourseStrList = new ArrayList<String>(adb.searchS_CourseList(uName));
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

        currentC_StrList = courseStrList;
        currentCList = courseList;

        //search by course name
        searchByNBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cName = course_Name2.getText().toString().trim();
                searchOrMy.setText("Search Result");
                if(cName.equals("")){
                    Toast.makeText(StudentActivity.this, "Invalid Input!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> tempList = new ArrayList<>();
                for(String temp: currentC_StrList){
                    if(temp.contains(cName)){
                        tempList.add(temp);
                    }
                }
                viewCourses(tempList);

            }
        });

        searchByDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cDay = course_day.getText().toString().trim();
                Days days1 = Days.stringToDays(cDay);
                searchOrMy.setText("Search Result");
                if(days1 == null) {
                    Toast.makeText(StudentActivity.this, "Invalid Input!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> tempList = cdb.searchCourseByDays(days1);
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
                course = null;
                courseSelectTxt.setText("Course Code: ");
                searchOrMy.setText("All Course");
                courseListView.setBackgroundColor(Color.parseColor("#CEDDE3"));
                currentC_StrList = courseStrList;
                currentCList = courseList;
                viewCourses(currentC_StrList);


            }
        });

        myCousrseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course = null;
                courseSelectTxt.setText("Course Code: ");
                searchOrMy.setText("My Course");
                courseListView.setBackgroundColor(Color.rgb(234,238,74));
                myCourseStrList = new ArrayList<String>(adb.searchS_CourseList(uName));
                currentC_StrList = myCourseStrList;
                currentCList = myCourseList;
                viewCourses(currentC_StrList);
            }
        });

        enrolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course == null){
                    Toast.makeText(StudentActivity.this, "Please Select a Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                enrollCourse(myCourseList, course);
                course.getStudentNameList().add(uName);
                cdb.overwriteStudent(course.getCode(), course.getStudentNameList().toString());
                course = null;
            }
        });

        unenrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course == null){
                    Toast.makeText(StudentActivity.this, "Please Select a Course!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                unEnrollCourse(course);
                viewCourses(myCourseStrList);
                course.getStudentNameList().remove(uName);
                cdb.overwriteStudent(course.getCode(), course.getStudentNameList().toString());
                course = null;
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
                course = currentCList.get(position);

                pos1 = position;
                printCourse();

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


    public boolean enrollCourse(List<Course> myCourseList2, Course course2){

        if(myCourseList2.contains(course2)){
            Toast.makeText(this, "Course Have Already Been Enrolled.", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(Course tempCourse:myCourseList2){
            for(Session tempSession:tempCourse.getSessionList()){
                for(Session tempSession2:course2.getSessionList()){
                    if(tempSession.isOverlap(tempSession2)){
                        Toast.makeText(this, "Course is overlapping", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        }

        myCourseList2.add(course2);
        String tempStr = course2.getName()+":"+course2.getCode();
        myCourseStrList.add(tempStr);
        adb.overwriteCourse(uName,myCourseStrList.toString());

        Toast.makeText(this, "Enroll Success", Toast.LENGTH_SHORT).show();

        return true;
    }

    public boolean unEnrollCourse(Course course){
        if(!myCourseList.contains(course)){
            Toast.makeText(this, "You are not enrolled in this course", Toast.LENGTH_SHORT).show();
            return false;
        }

        myCourseStrList.remove(pos1);
        currentC_StrList = myCourseStrList;
        myCourseList.remove(pos1);
        currentCList = myCourseList;
        adb.overwriteCourse(uName,myCourseStrList.toString());

        Toast.makeText(this, "Un-Enroll Success", Toast.LENGTH_SHORT).show();



        return true;
    }


}