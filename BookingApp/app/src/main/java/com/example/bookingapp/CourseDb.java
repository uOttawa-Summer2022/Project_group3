package com.example.bookingapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CourseDb extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "courses";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_INSTRUCTOR = "instructor";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CAPACITY = "Capacity";
    private static final String COLUMN_SESSION_ON_SUN = "sessionOnSun";
    private static final String COLUMN_SESSION_ON_MON = "sessionOnMon";
    private static final String COLUMN_SESSION_ON_TUE = "sessionOnTue";
    private static final String COLUMN_SESSION_ON_WED = "sessionOnWed";
    private static final String COLUMN_SESSION_ON_THU = "sessionOnThu";
    private static final String COLUMN_SESSION_ON_FRI = "sessionOnFri";
    private static final String COLUMN_SESSION_ON_SAT = "sessionOnSat";
    private static final String DATABASE_NAME = "courses.db";

    public CourseDb( Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_CODE + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_INSTRUCTOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CAPACITY  + " TEXT, " +
                COLUMN_SESSION_ON_SUN + " TEXT, " +
                COLUMN_SESSION_ON_MON + " TEXT, " +
                COLUMN_SESSION_ON_TUE + " TEXT, " +
                COLUMN_SESSION_ON_WED + " TEXT, " +
                COLUMN_SESSION_ON_THU + " TEXT, " +
                COLUMN_SESSION_ON_FRI + " TEXT, " +
                COLUMN_SESSION_ON_SAT + " TEXT" + ")";
        sqLiteDatabase.execSQL(create_table_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @SuppressLint("Range")
    public Course searchCourseByC(String code){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_NAME + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        Course course = new Course();

        if(cursor.moveToFirst()){
            course = new Course(cursor.getString(1), code);

            course.setInstructor(cursor.getString(2));
            course.setDescription(cursor.getString(3));
            course.setCapacity(Integer.getInteger(cursor.getString(4)));
            ArrayList<Session> sessionList = new ArrayList<>();
            for(int i = 5; i < 12;i++){
                Days days;
                switch (i){
                    case 5:
                        days = Days.Sunday;
                    case 6:
                        days = Days.Monday;
                    case 7:
                        days = Days.Tuesday;
                    case 8:
                        days = Days.Wednesday;
                    case 9:
                        days = Days.Thursday;
                    case 10:
                        days = Days.Friday;
                    default:
                        days = Days.Saturday;
                }
                String[] sessionDayList = cursor.getString(i).split("|");
                for(int j = 0;j<sessionDayList.length;j++){

                }

            }
            course.setCursor(cursor);






        }
        db.close();
        return course;
    }



    public Boolean AddCourse(Course course){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CODE, course.getCode());
        contentValues.put(COLUMN_NAME, course.getName());

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean EditCourse(String text, boolean useName, String newName , String newCode){
        boolean result = false;
        text = text.trim();
        String col = COLUMN_CODE;
        if(useName){
            col = COLUMN_NAME;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + col + " = \"" + text + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            result = true;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, newName);
            contentValues.put(COLUMN_CODE, newCode);
            db.update(TABLE_NAME, contentValues, col + "=?", new String[]{text});
            cursor.close();
        }
        db.close();
        return result;
    }

    public boolean EditCourseInstructor(String code, Instructor instructor){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            result = true;

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, instructor.getUser_name());

            db.update(TABLE_NAME, contentValues, COLUMN_CODE + "=?", new String[]{code});
            cursor.close();
        }
        db.close();

        return result;
    }

    public Boolean AddSession(String code, Session[] session){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String col;
        if(cursor.moveToFirst()) {
            result = true;
            ContentValues contentValues = new ContentValues();
            for(int i = 0; i < session.length;i++) {
                switch (session[i].getDay()) {
                    case Sunday:
                        col = COLUMN_SESSION_ON_SUN;
                    case Tuesday:
                        col = COLUMN_SESSION_ON_TUE;
                    case Wednesday:
                        col = COLUMN_SESSION_ON_WED;
                    case Thursday:
                        col = COLUMN_SESSION_ON_THU;
                    case Friday:
                        col = COLUMN_SESSION_ON_FRI;
                    case Saturday:
                        col = COLUMN_SESSION_ON_SAT;
                    default:
                        col = COLUMN_SESSION_ON_MON;
                }
                @SuppressLint("Range") String oldSessionData = cursor.getString(cursor.getColumnIndex(col));
                String newSessionData = session[i].toString() + "|";
                contentValues.put(col, oldSessionData + newSessionData);


            }
            db.update(TABLE_NAME, contentValues, COLUMN_CODE + "=?", new String[]{code});
            cursor.close();
        }
        db.close();
        return result;
    }

    public boolean deleteCourse(String code){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String codeStr = cursor.getString(0);
            result = true;
            db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[]{codeStr});
            cursor.close();
        }
        db.close();
        return result;
    }


    @SuppressLint("Range")
    public ArrayList<String> findAllCourses() {
        ArrayList<String> listOfCourses = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                listOfCourses.add(cursor.getString(cursor.getColumnIndex("value")));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listOfCourses;
    }

}
