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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.detail,container,false);
        return view;
    }
}