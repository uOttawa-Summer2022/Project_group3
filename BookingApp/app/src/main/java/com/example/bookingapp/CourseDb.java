package com.example.bookingapp;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private Cursor courseCursor;
    private int[] sessionDayIndex;
    public CourseDb( Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_CODE + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT UNIQUE, " +
                COLUMN_INSTRUCTOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CAPACITY  + " INTEGER, " +
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
        ArrayList<Session> List4 = new ArrayList<>();;
        ArrayList<ArrayList<Session>> finalsList = new ArrayList<>();;
        if(cursor.moveToFirst()){
            if(byN){
                course = new Course(cursor.getString(0), code);
            }else {
                course = new Course(code, cursor.getString(1));
            }

            course.setInstructor(cursor.getString(2));
            course.setDescription(cursor.getString(3));
            course.setCapacity(cursor.getInt(4));


            for(int i = 5; i < 12;i++){
                String temp = cursor.getString(i);
                if(temp == null||temp.equals("null")){
                    List4.add(null);
                    continue;
                }
                String[] list = temp.split(", ");
                int n = 0;
                while (n < list.length){
                    String[] list0 = list[n].split("/ ");
                    String[] list1 = list0[1].split(" ~ ");
                    String[] list2 = list1[0].split(":");
                    String[] list3 = list1[1].split(":");

                    List4.add( new Session( Integer.parseInt(list2[0]),
                                            Integer.parseInt(list2[1]),
                                            Integer.parseInt(list3[0]),
                                            Integer.parseInt(list3[1]),
                                            Days.stringToDays(list0[0])));
                    n++;
                }

                finalsList.add(List4);
                sessionDayIndex[i-5] = n;

            }
            course.setSessionList(finalsList);
            course.setSessionDayIndex(sessionDayIndex);
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

    public Boolean AddSession(String code, Session session){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String col;
        if(cursor.moveToFirst()) {
            result = true;
            ContentValues contentValues = new ContentValues();
            col = daysToCol(session.getDay());
            contentValues.put(col,session.toString());
            db.update(TABLE_NAME, contentValues, COLUMN_CODE + "=?", new String[]{code});
            cursor.close();
        }
        db.close();
        return result;
    }

    public boolean delSession(String code, Days days, String coverString){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + code + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String col = daysToCol(days);

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

    private String daysToCol(Days days){
        switch (days) {
            case Sunday:
                return COLUMN_SESSION_ON_SUN;
            case Tuesday:
                return COLUMN_SESSION_ON_TUE;
            case Wednesday:
                return COLUMN_SESSION_ON_WED;
            case Thursday:
                return COLUMN_SESSION_ON_THU;
            case Friday:
                return COLUMN_SESSION_ON_FRI;
            case Saturday:
                return COLUMN_SESSION_ON_SAT;
            default:
                return COLUMN_SESSION_ON_MON;
        }
    }

}
