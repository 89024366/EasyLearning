package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/3/28.
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ClearEditText extends android.support.v7.widget.AppCompatEditText {

    private final static String TAG = "EditTextWithDel";
    private Drawable imgInable;
    private Context mContext;
    private Cursor cursor=null;
    private SQLiteDatabase database=null;

    public  ClearEditText(Context context) {
        super(context);
        mContext = context;
        init();

    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    public void setdatabase(SQLiteDatabase db){
        database=db;
    }

    public Cursor getCursor(){
        if(database!=null) {
            String input = "ab";
            cursor = database.rawQuery("SELECT * FROM words where word='" + input + "%'", null);
        }
        return cursor;
    }


    private void init() {
        imgInable = mContext.getResources().getDrawable(R.drawable.delete);
        cursor=null;
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
                /*if(database!=null) {
                    String input = "ab";
                    cursor = database.rawQuery("SELECT * FROM words where word='" + input + "%'", null);
                }*/
                }
        });
        setDrawable();
    }

    // 设置删除图片
    public void setDrawable() {
        if (length() < 1)
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        else
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgInable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}