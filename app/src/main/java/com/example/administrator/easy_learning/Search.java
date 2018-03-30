package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/3/26.
 */
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.administrator.easy_learning.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search extends Fragment {
    private ListView listView;
    private ArrayList<String> dataList;
    private SimpleAdapter simpleAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search,container,false);
        listView = (ListView)view.findViewById(R.id.s_list);
        List<Map<String, Object>> list=getData();
        simpleAdapter = new SimpleAdapter( getActivity(),list,
                R.layout.list_item,
                new String[]{"title"},
                new int[]{R.id.tv});
        listView.setAdapter(simpleAdapter);

        return view;
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("title", "这是一个标题"+i);
            map.put("info",  i);
            list.add(map);
        }
        return list;
    }

}