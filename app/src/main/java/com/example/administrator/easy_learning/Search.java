package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/3/26.
 */
import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//com.example.administrator.easy_learning.ClearEditText
public class Search extends Fragment {
    private ClearEditText editText;
    private ListView listView;
    private List<Map<String, Object>> wordslist;
    private SimpleAdapter simpleAdapter;
    private SQLiteDatabase database;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search,container,false);
        editText = (ClearEditText)view.findViewById(R.id.s_edit);
        editText.setdatabase(database);
        listView = (ListView)view.findViewById(R.id.s_list);
        wordslist=getData(null);
        simpleAdapter = new SimpleAdapter( getActivity(),wordslist,
                R.layout.list_item,
                new String[]{"eng"},
                new int[]{R.id.tv});
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
/*
                Bundle bundle = new Bundle();
                bundle.putInt("photo", photo[arg2]);
                bundle.putString("message", message[arg2]);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, MoveList.class);
                Log.i("message", message[arg2]);
                startActivity(intent);*/
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setDrawable();
                if(database!=null) {
                    String input = editText.getText().toString();
                    Cursor cursor = database.rawQuery("SELECT * FROM words where word LIKE '" + input + "%'", null);
                    if (!input.equals("")) {
                        wordslist = getData(cursor);
                        simpleAdapter = new SimpleAdapter(getActivity(), wordslist,
                                R.layout.list_item,
                                new String[]{"eng","zh"},
                                new int[]{R.id.tv,R.id.tv_zh});
                        listView.setAdapter(simpleAdapter);
                    }
                }
            }
        });




        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        database=((MainActivity) activity).getDatabase();//通过强转成宿主activity，就可以获取到传递过来的数据
    }


    public List<Map<String, Object>> getData(Cursor cursor){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        //Cursor cursor=editText.getCursor();

        if(cursor != null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("eng",cursor.getString(cursor.getColumnIndex("Word")));
                map.put("zh",cursor.getString(cursor.getColumnIndex("meaning")));
                list.add(map);
            }
        }
        else{

        }
        return list;
    }

}