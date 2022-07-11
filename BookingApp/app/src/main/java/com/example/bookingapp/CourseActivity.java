package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CourseActivity extends AppCompatActivity {
    EditText courseCapacity,courseDescription,courseSessionDay,startTimeHour,endTimeHour,startTimeMinute,endTimeMinute;
    Button button_goBackInstructorActivity,button_deleteSession,button_addSession,logOut,button_EditCapAndDes,button_AddCapAndDes;
    com.example.bookingapp.CourseDb cdb;
    com.example.bookingapp.AccountDB adb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
    }


    public static boolean isValidSession (int sM,int eM,int sH,int eH){
        if(sM < 0 || sM > 59){return false;}

        if(eM< 0 || eM > 59){return false;}

        if(sH< 0 || eH > 23){return false;}

        return eH >= 0 && eH <= 23;
    }


}