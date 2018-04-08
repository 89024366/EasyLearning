package com.example.administrator.easy_learning;

/**
 * Created by Administrator on 2018/4/8.
 */

public class word {
    private String words;
    private String meaning;
    private String lx;

    public word(){}
    public word(String word, String mean, String sentence){
        words=word;
        meaning=mean;
        lx=sentence;
    }

    public String getLx() {
        return lx;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getWords() {
        return words;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }
}
