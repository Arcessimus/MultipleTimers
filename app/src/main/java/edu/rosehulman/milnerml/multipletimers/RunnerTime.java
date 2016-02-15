package edu.rosehulman.milnerml.multipletimers;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kumarms on 1/25/2016.
 */
public class RunnerTime implements Parcelable{
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
    public SplitsAdapter adapter;

    protected RunnerTime(Parcel in) {
        mSplits = in.createStringArrayList();
        mTotalTimesSplits = in.createStringArrayList();
        name = in.readString();
        split = in.readByte() != 0;
        mins = in.readInt();
        secs = in.readInt();
        milliseconds = in.readInt();
        updatedTime = in.readLong();
        clockStopped = in.readByte() != 0;
    }

    public static final Creator<RunnerTime> CREATOR = new Creator<RunnerTime>() {
        @Override
        public RunnerTime createFromParcel(Parcel in) {
            return new RunnerTime(in);
        }

        @Override
        public RunnerTime[] newArray(int size) {
            return new RunnerTime[size];
        }
    };

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
        adapter = new SplitsAdapter();
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

    public String addSplit(long updatedtime, String currentTotalTime) {
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
        adapter.addSplits(currentTime, currentTotalTime);
        return currentTime;
    }

    public void addTotalSplits(String currentTime){
        mTotalTimesSplits.add(currentTime);
    }

    public void setAdapter(SplitsAdapter adapter) {
        this.adapter = adapter;
    }

    public SplitsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(mSplits);
        dest.writeStringList(mTotalTimesSplits);
        dest.writeString(name);
        dest.writeByte((byte) (split ? 1 : 0));
        dest.writeInt(mins);
        dest.writeInt(secs);
        dest.writeInt(milliseconds);
        dest.writeLong(updatedTime);
        dest.writeByte((byte) (clockStopped ? 1 : 0));
    }
}
