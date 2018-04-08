package com.example.administrator.easy_learning;

import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Detail f_detail;
    private Notebook f_notebook;
    private Search f_search;
    private DBOpenHelper databaseHelper;
    private SQLiteDatabase database;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    showNav(R.id.navigation_home);
                    return true;
                case R.id.navigation_dashboard:
                   // mTextMessage.setText(R.string.title_dashboard);
                    showNav(R.id.navigation_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    showNav(R.id.navigation_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DBOpenHelper(this);
        database=databaseHelper.getSqLiteDatabase();
        init();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public SQLiteDatabase getDatabase(){
        return database;
    }
    private void init(){
        f_detail=new Detail();
        f_notebook=new Notebook();
        f_search=new Search();
        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.content,f_search).add(R.id.content,f_detail).add(R.id.content,f_notebook);//开启一个事务将fragment动态加载到组件
       // beginTransaction.hide(f_search);
        //beginTransaction.hide(f_detail);
        //beginTransaction.hide(f_notebook);//隐藏fragment
        //beginTransaction.replace(R.id.content,f_search);
        beginTransaction.addToBackStack(null);//返回到上一个显示的fragment
        beginTransaction.commit();//每一个事务最后操作必须是commit（），否则看不见效果
        showNav(R.id.navigation_home);
    }

    private void showNav(int navid){
        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();

        switch (navid){
            case R.id.navigation_home:
                beginTransaction.hide(f_detail).hide(f_notebook).show(f_search);
                //beginTransaction.replace(R.id.content,f_search);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_dashboard:
                beginTransaction.hide(f_search).hide(f_notebook).show(f_detail);
                //beginTransaction.replace(R.id.content,f_detail);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_notifications:
                beginTransaction.hide(f_detail).hide(f_search).show(f_notebook);
                //beginTransaction.replace(R.id.content,f_notebook);
               beginTransaction.addToBackStack(null);
               beginTransaction.commit();
                break;
        }
    }
}
