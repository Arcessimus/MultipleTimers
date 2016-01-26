package edu.rosehulman.milnerml.multipletimers;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by kumarms on 1/25/2016.
 */
public class Timer {
    Integer timelaps;
    ArrayList<Integer> mSplits;
    String name;

    public Timer(String name) {
        if (name.equals("")){
            name = "name not entered";
        }else{
            this.name = name;
        }
    }

    public Timer() {
        this.name = "name not entered";
    }

    public String getText() {
        return name;
    }
}
