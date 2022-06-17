package com.example.bookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AccountDb extends SQLiteOpenHelper {


    private static final String TABLE_NAME = "users";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "accType";
    private static final String DATABASE_NAME = "accounts.db";

    public AccountDb(Context context){
        super(context, DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_TYPE + " TEXT " + ")";
        sqLiteDatabase.execSQL(create_table_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addAccount (Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, account.getUser_name());
        contentValues.put(COLUMN_PASSWORD, account.getPassword());
        contentValues.put(COLUMN_TYPE, String.valueOf(account.getType()));
        db.insert(TABLE_NAME,null,contentValues);

    }
    public Account findAccount(String name) {
        Account account =new Account();
        SQLiteDatabase db = this.getWritableDatabase();
        String likeName = name + "%";
        String query = " SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME +
                " LIKE "+ "'" +likeName+ "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){

            account.setUser_name(name);
            account.setPassword(cursor.getString(1));
            account.setType(Enum.valueOf(cursor.getString(2)) );
            cursor.close();
            db.close();
            return account;
        }
        db.close();
        return null;

    }
    public boolean deleteAccount(String name){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String nameStr = cursor.getString(0);
            result = true;
            db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[]{nameStr});
            cursor.close();
        }
        db.close();
        return result;
    }
}

