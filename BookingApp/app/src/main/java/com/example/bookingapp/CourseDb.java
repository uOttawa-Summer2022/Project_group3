package com.example.bookingapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseDb extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "courses";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_INSTRUCTOR = "instructor";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CAPACITY = "Capacity";
    private static final String COLUMN_SESSION = "session";
    private static final String COLUMN_STUDENT = "student";

    private static final String DATABASE_NAME = "courses.db";
    private Cursor courseCursor;
    private int[] sessionDayIndex;
    public CourseDb( Context context){
        super(context, DATABASE_NAME,null,1);
    }
    public static String strSeparator = ",";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_CODE + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT UNIQUE, " +
                COLUMN_INSTRUCTOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CAPACITY  + " INTEGER, " +
                COLUMN_SESSION +  " TEXT, " +
                COLUMN_STUDENT +  " TEXT" +")";
        sqLiteDatabase.execSQL(create_table_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public Course searchCourse(String code, boolean byN){
        sessionDayIndex = new int[7];
        String col = COLUMN_CODE;
        if(byN){
            col = COLUMN_NAME;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + col + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        Course course = null;
        ArrayList<Session> sessionList = new ArrayList<>();;

        if(cursor.moveToFirst()){
            if(byN){
                course = new Course(cursor.getString(0), code);
            }else {
                course = new Course(code, cursor.getString(1));
            }

            course.setInstructor(cursor.getString(2));
            course.setDescription(cursor.getString(5));
            course.setCapacity(cursor.getInt(4));
            String sessionString = cursor.getString(5);
            if(sessionString == null||sessionString.equals("null")){
                course.setSessionList(null);
            }else {
                String[] tempA = convertStringToArray(sessionString);
                for (String tempS:tempA) {
                    sessionList.add(new Session(tempS));
                }
                course.setSessionList(sessionList);
            }



        }
        cursor.close();
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

    public boolean EditCourseInstructor(String code, String instructor){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            result = true;

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_INSTRUCTOR, instructor);

            db.update(TABLE_NAME, contentValues, COLUMN_CODE + "=?", new String[]{code});
            cursor.close();
        }
        db.close();

        return result;
    }

    public boolean editCrsDescription(String description,String code){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, description);
        db.update(TABLE_NAME, contentValues, "code=?", new String[]{code});
        return true;
    }

    public boolean editCrsCapacity(String code, int capacity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CAPACITY, capacity);
        db.update(TABLE_NAME, contentValues, "code=?", new String[]{code});
        return true;
    }

    public Boolean overwriteSession(String code, String overwriteString){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SESSION, overwriteString);
        db.update(TABLE_NAME, contentValues, "code=?", new String[]{code});
        db.close();
        return result;
    }

    public boolean delSession(String code, Days days, String coverString){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String col = COLUMN_SESSION;

        if(cursor.moveToFirst()){
            result = true;
            ContentValues contentValues = new ContentValues();
            contentValues.put(col, coverString);

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
            db.delete(TABLE_NAME, COLUMN_CODE + "=?", new String[]{codeStr});
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
                listOfCourses.add(cursor.getString(1)+":"+cursor.getString(0));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listOfCourses;
    }
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        if(str.substring(0,1).equals("[")){
            str = str.substring(1,str.length());
        }
        if(str.substring(str.length()-1).equals("]")){
            str = str.substring(0 ,str.length()-1);
        }

        return str.split(strSeparator);
    }


}

