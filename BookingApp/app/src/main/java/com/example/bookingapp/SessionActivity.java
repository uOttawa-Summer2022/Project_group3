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
    ArrayList<ArrayList<Session>> sessionList;
    ArrayList<String> sessionListS;
    Button GotoSearchBtn, addSessionBtn, delSessionBtn;
    TextView sessionTxt, sessionInfoTxt;
    ArrayAdapter<String> sessionAdapter;
    CourseDb cdb;
    Course course;
    String session;
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
        //sessionListS = arrayConvert(sessionList);
        //loadSessionListView(sessionListS);

        final Intent[] intent = {getIntent()};


        String userName = intent[0].getStringExtra("userName");
        String role = intent[0].getStringExtra("role");
        printSession();

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
                    if(session != null){
                        int n = 0, j = 0, temp = 0;


                        while(n >= 0) {
                            temp = n;
                            n = position - course.getSessionDayIndex()[j];
                            j++;
                        }
                        j--;
                        sessionList.get(j).remove(temp);
                        cdb.delSession(course.getCode(), Days.numToDays(j), arrayToString2(sessionList.get(j)));


                    }else {
                        Toast.makeText(SessionActivity.this, "No Selected Session", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(SessionActivity.this, "Permitted", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SessionActivity.this, "Not-Permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ArrayList<String> arrayConvert(ArrayList<ArrayList<Session>> comList){
        ArrayList<String> result = new ArrayList<>();
        int i = 0;

        while(i<7){
            int n = 0;
            ArrayList<Session> temp = comList.get(i);
            if(temp == null){
                i++;
                continue;
            }
            while(true){
                try {
                    result.add(temp.get(n).toString());
                }catch (Exception e){
                    break;
                }
            }
        }
        return result;
    }

    private void loadSessionListView(ArrayList<String> n){
        course = cdb.searchCourse(course.getCode(),false);
        sessionList = course.getSessionList();
        sessionListS = arrayConvert(sessionList);

        sessionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,n);
        sessionListView.setAdapter(sessionAdapter);
    }

    private boolean printSession(){
        if(sessionList == null){
            Toast.makeText(this, "No Session In This Course", Toast.LENGTH_SHORT).show();
            return false;
        }

        sessionInfoTxt.setText(sessionList.toString());
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        session = sessionListS.get(position);
        this.position = position;
        printSession();


    }

    public String arrayToString2(ArrayList<Session> A){
        String As = A.toString();
        return As.substring(1, As.length()-1);
    }
}