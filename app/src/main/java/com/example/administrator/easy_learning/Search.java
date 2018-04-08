package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/3/26.
 */
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;

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
    private Handler handler;

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
        handler = new Handler();
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
                    final String input = editText.getText().toString();
                    Cursor cursor = database.rawQuery("SELECT * FROM words where word LIKE '" + input + "%'", null);
                    if (!input.equals("")) {
                        wordslist = getData(cursor);
                        if(cursor != null&&cursor.getCount()>0){
                            simpleAdapter = new SimpleAdapter(getActivity(), wordslist,
                                    R.layout.list_item,
                                    new String[]{"eng","zh"},
                                    new int[]{R.id.tv,R.id.tv_zh});
                            listView.setAdapter(simpleAdapter);
                        }else{
                            final ListView lv = listView;
                            final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
                            MainActivity.TRANSLATOR.lookUpFromEn(input, new TranslateListener() {
                                @Override
                                public void onError(TranslateErrorCode translateErrorCode, String s) {

                                }

                                @Override
                                public void onResult(final Translate translate, String s, String s1) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Map<String, Object> map=new HashMap<String, Object>();
                                            map.put("eng",input);
                                            map.put("zh",translate.getExplains().toString());
                                            list.add(map);
                                            SimpleAdapter sa = new SimpleAdapter(getActivity(), list,
                                                    R.layout.list_item,
                                                    new String[]{"eng","zh"},
                                                    new int[]{R.id.tv,R.id.tv_zh});
                                            lv.setAdapter(sa);
                                        }
                                    });
                                }

                                @Override
                                public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

                                }
                            });
                        }

                    }
                }
            }

        });


     /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
                HashMap<String, String> map = (HashMap<String, String>) parent
                        .getItemAtPosition(position);
                Toast.makeText(view.getContext(), map.get("shopName"),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                editText.clearFocus();
                HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
                String eng = map.get("eng");
                String zh = map.get("zh");
                String lx = map.get("lx").replace("/r/n","\n");
                //Cursor cursor = database.rawQuery("SELECT * FROM words where word = '" + eng + "'", null);
                ((MainActivity)getActivity()).seteng(eng);
                    ((MainActivity)getActivity()).setzh(zh);
                    ((MainActivity)getActivity()).setlx(lx);
                ((MainActivity)getActivity()).selectFragment(R.id.navigation_dashboard);

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
                map.put("lx",cursor.getString(cursor.getColumnIndex("lx")));
                list.add(map);
            }
        }
        else {

        }
        return list;
    }

}