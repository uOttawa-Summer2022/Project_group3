package com.example.bookingapp;
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
    private static final String DATABASE_NAME = "courses.db";
    public CourseDb( Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_CODE + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL(create_table_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void AddCourse(Course course){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, course.getName());
        contentValues.put(COLUMN_CODE, course.getCode());
        db.insert(TABLE_NAME,null,contentValues);
    }
    public Boolean EditCourse(Course course, String newName , String newCode){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_CODE + " = \"" + course.getCode() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            result = true;
            db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[]{course.getCode()});
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, newName);
            contentValues.put(COLUMN_CODE, newCode);
            db.insert(TABLE_NAME,null,contentValues);
            course.setName(newName);
            course.setCode(newCode);
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

    //need to impelement find all courses
}
