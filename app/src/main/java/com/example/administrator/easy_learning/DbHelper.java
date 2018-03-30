package com.example.administrator.easy_learning;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/3/29.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "My.db";
    public static String TABLE_NAME = "words";
    public String COL_1 = "id";
    public String COL_2 = "word";
    public String COL_3 = "pronounce";
    public String COL_4 = "paraphrase";
    public String COL_5 = "sentence";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE words(id INTEGER PRIMARY KEY AUTOINCREMENT,word VARCHAR(20),pronounce VARCHAR(15),paraphrase VARCHAR(50),sentence VARCHAR(100))");

    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE words ADD phone VARCHAR(12) ");
    }

    public boolean insertData(String word, String surname, String marks) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, word);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);

        //Table name, null and the content values are needed as param

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            //Insert has failed
            return false;
        } else {
            //Successful insertion
            return true;
        }
    }

    public Cursor readData(String input) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM words where word='"+input+"'", null);
        return cursor;
    }

    public Cursor fuzzyRead(String input) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT word FROM " + TABLE_NAME+"where word='"+input+"%'", null);
        return cursor;
    }
}

