package edu.rosehulman.milnerml.multipletimers;

import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kumarms on 1/25/2016.
 */
public class RunnerTime {
    private TextView clock;
    Integer timelaps;
    ArrayList<Integer> mSplits;
    String name;

    public Integer getTimelaps() {
        return timelaps;
    }

    public void setTimelaps(Integer timelaps) {
        this.timelaps = timelaps;
    }

    public ArrayList<Integer> getmSplits() {
        return mSplits;
    }

    public void setmSplits(ArrayList<Integer> mSplits) {
        this.mSplits = mSplits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RunnerTime(String name) {
        if (name.equals("")){
            name = "name not entered";
        }else{
            this.name = name;
        }
    }

    public void setClock(TextView clock) {
        this.clock = (clock);
    }

    public RunnerTime() {
        this.name = "name not entered";
    }

    public String getText() {
        return name;
    }

    public void setClockTime(String clockTime) {
        this.clock.setText(clockTime);
    }
}
