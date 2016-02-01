package edu.rosehulman.milnerml.multipletimers;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kumarms on 1/25/2016.
 */
public class RunnerTime {
    private TextView clock;
    Integer timelaps;
    ArrayList<String> mSplits;
    ArrayList<String> mTotalTimesSplits;
    String name;
    private boolean split = false;
    int mins = 0;
    int secs = 0;
    int milliseconds = 0;
    long updatedTime = 0;

    private boolean clockStopped = false;

    public Integer getTimelaps() {
        return timelaps;
    }

    public void setTimelaps(Integer timelaps) {
        this.timelaps = timelaps;
    }

    public ArrayList<String> getmSplits() {
        return mSplits;
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
        mSplits = new ArrayList<>();
        mTotalTimesSplits = new ArrayList<>();
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

    public boolean isClockStopped() {
        return clockStopped;
    }

    public void setClockStopped(boolean clockStopped) {
        this.clockStopped = clockStopped;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public String addSplit(long updatedtime) {
        long updatedtimetemp = updatedtime;
        updatedtime = updatedtime - updatedTime;
        updatedTime = updatedtimetemp;
        int secs = (int) (updatedtime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (updatedtime % 1000);
        String currentTime = "" + mins + ":" + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds);
        mSplits.add(currentTime);
        return currentTime;
    }

    public void addTotalSplits(String currentTime){
        mTotalTimesSplits.add(currentTime);
    }

}
