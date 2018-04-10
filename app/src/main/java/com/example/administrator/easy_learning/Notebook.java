package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/3/26.
 */
import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.administrator.easy_learning.R;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notebook extends Fragment{
    private ListView listView;
    private List<Map<String, Object>> wordList;
    private SimpleAdapter simpleAdapter;
    private SQLiteDatabase database;
    private Handler handler;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.notebook,container,false);
        handler = new Handler();
        listView = (ListView)view.findViewById(R.id.list_notebook);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id){
                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                final String word = map.get("word");
                if(word.matches("^[a-zA-Z]*")){
                    Cursor cursor = database.rawQuery("select * from words where word=\""+word+"\"",null);
                    if(cursor == null || cursor.getCount() ==0){
                        cursor = database.rawQuery("select * from notebook where word=\""+word+"\"",null);
                    }
                    cursor.moveToNext();
                    ((MainActivity)getActivity()).seteng(word);
                    ((MainActivity)getActivity()).sethd(word);
                    ((MainActivity)getActivity()).setlx((cursor.getColumnCount() == 2 ?
                            "没有例句":
                            cursor.getString(cursor.getColumnIndex("lx")).replace("/r"," ").replace("/n","\n")));
                    ((MainActivity)getActivity()).setzh(cursor.getString(cursor.getColumnIndex("meaning")));
                    ((MainActivity)getActivity()).inNotebook(true);
                    ((MainActivity)getActivity()).selectFragment(R.id.navigation_dashboard);
                }else{
                    Cursor cursor = database.rawQuery("select * from notebook where word=\""+word+"\"",null);
                    cursor.moveToNext();
                    ((MainActivity)getActivity()).seteng(word);
                    ((MainActivity)getActivity()).setzh(cursor.getString(cursor.getColumnIndex("meaning")));
                    ((MainActivity)getActivity()).setlx("没有例句");
                    ((MainActivity)getActivity()).inNotebook(true);
                    ((MainActivity)getActivity()).sethd(word);
                    ((MainActivity)getActivity()).selectFragment(R.id.navigation_dashboard);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id){
                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                final String word = map.get("word");
                deleteFromNotebook(word);
                refresh();
                return true;
            }
        });

        refresh();
        return view;
    }

    public void refresh(){
        Cursor cursor = database.rawQuery("SELECT * FROM notebook",null);
        wordList = getData(cursor);
        simpleAdapter = new SimpleAdapter(getActivity(), wordList,
                R.layout.list_item_notebook,
                new String[]{"word","meaning"},
                new int[]{R.id.ls_word,R.id.ls_translation});
        listView.setAdapter(simpleAdapter);
    }



    private void deleteFromNotebook(String word){
        database.delete("notebook","word=?",new String[]{word});
    }
    public List<Map<String, Object>> getData(Cursor cursor){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(cursor != null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("word",cursor.getString(cursor.getColumnIndex("word")));
                map.put("meaning",cursor.getString(cursor.getColumnIndex("meaning")));
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        database=((MainActivity) activity).getDatabase();//通过强转成宿主activity，就可以获取到传递过来的数据
    }
}
