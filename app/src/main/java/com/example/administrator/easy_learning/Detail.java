package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/3/26.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.easy_learning.R;

public class Detail extends Fragment{
    private final static String TAG = "Detail";
    private SQLiteDatabase database;
    private TextView eng;
    private TextView zh;
    private TextView lx;
    private TextView hd;
    private Button btn,btn_add;
    private Boolean ifInNotebook = false;

    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.detail,container,false);
        eng=(TextView)view.findViewById(R.id.tv_eng);
        zh=(TextView)view.findViewById(R.id.tv_meaning);
        lx=(TextView)view.findViewById(R.id.tv_lx);
        hd = (TextView)view.findViewById(R.id.tv_head);
        btn = (Button)view.findViewById(R.id.btn_back);
        btn_add = (Button)view.findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                backToSearch();
            }
        });



        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ifInNotebook){
                    deleteFromNotebook(eng.getText().toString());
                    Toast.makeText(getActivity(),"已从单词本删除",Toast.LENGTH_SHORT).show();
                    notInNotebook();
                }else {
                    addToNotebook(eng.getText().toString(),zh.getText().toString());
                    Toast.makeText(getActivity(),"已添加至单词本",Toast.LENGTH_SHORT).show();
                    inNotebook();
                }
            }
        });
        return view;
    }

    public void inNotebook(){
        btn_add.setBackgroundResource(R.drawable.trash);
        ifInNotebook = true;
    }

    public void notInNotebook(){
        btn_add.setBackgroundResource(R.drawable.add);
        ifInNotebook = false;
    }


    private void addToNotebook(String word,String meaning){
        ContentValues content = new ContentValues();
        content.put("word",word);
        content.put("meaning",meaning);
        database.insert("notebook",null,content);
    }

    private void deleteFromNotebook(String word){
        database.delete("notebook","word=?",new String[]{word});
    }

    public void backToSearch(){
        ((MainActivity)getActivity()).selectFragment(R.id.navigation_home);
    }

    public void sethead(String str) {
        hd.setText(str);
    }
    public void seteng(String str) {
        eng.setText(str);
    }

    public void setzh(String str) {

        zh.setText(str);
    }

    public void setlx(String str){
        lx.setText(str);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        database=((MainActivity) activity).getDatabase();//通过强转成宿主activity，就可以获取到传递过来的数据
    }
}