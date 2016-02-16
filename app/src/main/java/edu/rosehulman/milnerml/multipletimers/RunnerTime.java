package edu.rosehulman.milnerml.multipletimers;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kumarms on 1/25/2016.
 */
public class RunnerTime implements Parcelable{
    private Button lastSplitsButton;

    public Button getLastSplitsButton() {
        return lastSplitsButton;
    }

    public void setLastSplitsButton(Button lastSplitsButton) {
        this.lastSplitsButton = lastSplitsButton;
    }

    private TextView clock;
    Integer timelaps;
    ArrayList<String> mSplits;
    ArrayList<String> mTotalTimesSplits;
    String name;
    private boolean split = false;
    int mins = 0;
    int secs = 0;
    int milliseconds = 0;

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    long updatedTime = 0;
    private String lastSplit;

    private boolean clockStopped = false;
//    public SplitsAdapter adapter; removing for simplicity

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

    public String getLastSplit() {
        return lastSplit;
    }

    public void setLastSplit(String lastSplit) {
        this.lastSplit = "lastSplit: " + lastSplit;
    }

    public RunnerTime(String name) {
        if (name.equals("")){
            name = "name not entered";
        }else{
            this.name = name;
        }
        mSplits = new ArrayList<>();
        mTotalTimesSplits = new ArrayList<>();

        lastSplit = "00:00.000";
//        adapter = new SplitsAdapter();
    }

    public ArrayList<String> getmTotalTimesSplits() {
        return mTotalTimesSplits;
    }

    public void setClock(TextView clock) {
        this.clock = (clock);
    }

    public RunnerTime() {
        this.name = "name not entered";
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
//        adapter.addSplits(currentTime, currentTotalTime);
        return currentTime;
    }

    public void addTotalSplits(String currentTime){
        mTotalTimesSplits.add(currentTime);
    }

//    public void setAdapter(SplitsAdapter adapter) {
//        this.adapter = adapter;
//    }
//
//    public SplitsAdapter getAdapter() {
//        return adapter;
//    }

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

    public JSONObject toJSON()
    {
        JSONObject obj = new JSONObject();

        try {
            obj.put("clock", clock.getText());
            obj.put("timelaps", timelaps);
            obj.put("splits", mSplits.toArray());
            obj.put("total_time_splits", mTotalTimesSplits.toArray());
            obj.put("name", name);
            obj.put("split", split);
            obj.put("mins",mins);
            obj.put("secs", secs);
            obj.put("milliseconds", milliseconds);
            obj.put("updatedTime", updatedTime);
            obj.put("lastSplit", lastSplit);
            obj.put("clockStopped", clockStopped);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return obj;
    }

    public void toObjFromJSON(JSONObject obj)
    {
        try {
            clock.setText(obj.getString("clock"));
            timelaps = obj.getInt("timelaps");
            name = obj.getString("name");
            split = obj.getBoolean("split");
            mins = obj.getInt("mins");
            secs = obj.getInt("secs");
            milliseconds = obj.getInt("milliseconds");
            updatedTime = obj.getLong("updatedTime");
            lastSplit = obj.getString("lastSplit");
            clockStopped = obj.getBoolean("clockStopped");

            JSONArray splitsArray = obj.getJSONArray("splits");
            JSONArray totalSplitsArray = obj.getJSONArray("total_time_splits");

            mSplits = new ArrayList<>();
            for (int i = 0; i < splitsArray.length(); i++)
                mSplits.add(splitsArray.getString(i));

            mTotalTimesSplits = new ArrayList<>();
            for(int j = 0; j < totalSplitsArray.length(); j++)
                mTotalTimesSplits.add(totalSplitsArray.getString(j));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
