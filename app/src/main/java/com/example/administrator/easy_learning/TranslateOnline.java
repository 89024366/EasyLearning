package com.example.administrator.easy_learning;
import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

/**
 * Created by 海燕 on 2018/4/8.
 */

public class TranslateOnline {
    private Context mContext;
    private String mAppKey = "0b40cabf99c5213e";
    private Language mEnglish;
    private Language mChinese;
    public TranslateOnline(Context context){
        mContext = context.getApplicationContext();
        YouDaoApplication.init(mContext,mAppKey);
        mEnglish = LanguageUtils.getLangByName("英文");
        mChinese = LanguageUtils.getLangByName("中文");
    }

    public void lookUpFromEn(String input, TranslateListener listener){
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("youdao")
                .from(mEnglish).to(mChinese)
                .build();
        Translator translator = Translator.getInstance(tps);
        translator.lookup(input,mAppKey,listener);
    }

    public void lookUpFromCn(String input, TranslateListener listener){

        TranslateParameters tps = new TranslateParameters.Builder()
                .source("youdao")
                .from(mChinese).to(mEnglish)
                .build();
        Translator translator = Translator.getInstance(tps);
        translator.lookup(input,mAppKey,listener);
    }

    public void lookup(String input,TranslateListener listener){
        if(isEnglish(input)){
            lookUpFromEn(input,listener);
        }else{
            lookUpFromCn(input,listener);
        }
    }

    private boolean isEnglish(String str){
        return str.matches("^[a-zA-Z]*");
    }

    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null){
            return networkInfo.isAvailable();
        }
        return false;
    }
}
