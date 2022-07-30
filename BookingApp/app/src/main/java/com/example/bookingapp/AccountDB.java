package com.example.bookingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Accounts.db";

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_fNAME = "firstname";
    private static final String COLUMN_lNAME = "lastname";
    private static final String COLUMN_uNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_COURSE = "course_code";

    public AccountDB(Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        sqlDB.execSQL("create Table users("+
                COLUMN_fNAME+"TEXT, "+
                COLUMN_lNAME+ "TEXT, "+
                COLUMN_uNAME+ "TEXT primary key, " +
                COLUMN_PASSWORD+"TEXT, "+
                COLUMN_ROLE+ "TEXT, "+
                COLUMN_COURSE+"TEXT"+")");

        //below adds the single admin account to the DB
        String adminAccount = "INSERT INTO users (firstname, lastname, username, password, role) VALUES('John', 'Doe', 'admin', 'admin123', 'admin')";
        sqlDB.execSQL(adminAccount);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i1) {
        sqlDB.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqlDB);
    }

    public Boolean addAccount (String firstname, String lastname, String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("role", role);

        long result = db.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Login Methods

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernameAndPassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public String getRoleWithUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select role from users where username = ?", new String[]{username});

        String result = null;
        if (cursor.moveToFirst()) {
            int roleColumn = cursor.getColumnIndex("role");
            result = cursor.getString(roleColumn);
        }

        return result;

    }

    public String getFirstNameWithUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select firstname from users where username = ?", new String[]{username});

        String result = null;
        if (cursor.moveToFirst()) {
            int fnColumn = cursor.getColumnIndex("firstname");
            result = cursor.getString(fnColumn);
        }

        return result;

    }




    //Admin Methods - Previously Made ....

    public com.example.bookingapp.Account findAccount(String name) {
        com.example.bookingapp.Account account =new com.example.bookingapp.Account();
        SQLiteDatabase db = this.getWritableDatabase();
        String likeName = name + "%";
        String query = "SELECT * FROM users WHERE firstname LIKE likeName";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){

            account.setUser_name(name);
            account.setPassword(cursor.getString(1));
            account.setType(AccountType.valueOf(cursor.getString(2)) );
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
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_uNAME + " = \"" + name + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String nameStr = cursor.getString(2);
            result = true;
            db.delete("users", "firstname" + "=?", new String[]{nameStr});
            cursor.close();
        }
        db.close();
        return result;
    }
}

