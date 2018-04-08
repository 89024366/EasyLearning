package com.example.administrator.easy_learning;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/29.
 */

public class DBOpenHelper   {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "englishword.db"; // 保存的数据库文件名称
    public static String TABLE_NAME = "words";
    public String COL_1 = "word";
    public String COL_2 = "meaning";
    public String COL_3 = "lx";

    public static final String PACKAGE_NAME = "com.example.administrator.easy_learning";// 应用的包名
    public static final String DB_PATH = "/data"
             +Environment.getDataDirectory().getAbsolutePath() +"/"
            + PACKAGE_NAME+ "/databases"; // 在手机里存放数据库的位置
    //sdcard的路径(在android 4.4中不好使，文件成功创建是在手机的)
    //public static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/idiom";

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DBOpenHelper(Context context) {

        this.context = context;
        this.openDatabase();
    }


    public SQLiteDatabase openDatabase() {
        try {
            File myDataPath = new File(DB_PATH);
            if (!myDataPath.exists())
            {
                myDataPath.mkdirs();// 假设没有这个文件夹,则创建
            }
            String dbfile=myDataPath+"/"+DB_NAME;
            if (!(new File(dbfile).exists())) {// 推断数据库文件是否存在，若不存在则运行导入，否则直接打开数据库
                InputStream is = context.getResources().openRawResource(R.raw.englishword); // 欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            sqLiteDatabase  = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public SQLiteDatabase getSqLiteDatabase(){
        return sqLiteDatabase;
    }

    public void closeDatabase() {
        this.sqLiteDatabase.close();
    }

    public Cursor readData(String input) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM words where word='"+input+"'", null);
        return cursor;
    }

    public Cursor fuzzyRead(String input) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT word FROM " + TABLE_NAME+"where word='"+input+"%'", null);
        return cursor;
    }
}
