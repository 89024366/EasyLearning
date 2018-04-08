package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/3/26.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.easy_learning.R;

public class Detail extends Fragment{
    private final static String TAG = "Detail";
    private TextView eng;
    private TextView zh;
    private TextView lx;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.detail,container,false);
        eng=(TextView)view.findViewById(R.id.tv_eng);
        zh=(TextView)view.findViewById(R.id.tv_meaning);
        lx=(TextView)view.findViewById(R.id.tv_lx);
        return view;
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
}