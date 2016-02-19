package edu.rosehulman.milnerml.multipletimers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by theoderic on 1/27/16.
 */
public class SavedRaceAdapter extends RecyclerView.Adapter<SavedRaceAdapter.ViewHolder> {
    private ArrayList<RunnerTime> mTimers = new ArrayList<>();
    private Context mContext;
    private Callback mCallBack;

    public ArrayList<RunnerTime> getmTimers() {
        return mTimers;
    }

    public SavedRaceAdapter() {
    }

    public SavedRaceAdapter(Context mContext, Callback callback) {
        this.mContext = mContext;
        this.mCallBack = callback;
    }

    public void add(RunnerTime timer) {
        mTimers.add(timer);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_races, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSaveName.setText("something something blah something");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSaveName;
        private Button mEditButton;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mSaveName = (TextView)itemView.findViewById(R.id.race_name);
            this.mEditButton = (Button)itemView.findViewById(R.id.edit_button);
        }
    }

    public void update(RunnerTime timer, String name) {
        timer.setName(name);
        notifyDataSetChanged();
    }

    public void remove(RunnerTime timer) {
        mTimers.remove(timer);
    }

    public void updateRunnerCards(String currentTime, long updatedTime) {
        RunnerTime currentRunner;
        for (int i = 0; i < mTimers.size(); i++) {
            currentRunner = mTimers.get(i);
            if (!currentRunner.isClockStopped()) {
                currentRunner.setClockTime(currentTime);
            }
            if (currentRunner.isSplit()){
                currentRunner.setSplit(false);
                String splitForLog = currentRunner.addSplit(updatedTime, currentTime);
                currentRunner.addTotalSplits(currentTime);
                currentRunner.setLastSplit(splitForLog);
                currentRunner.getLastSplitsButton().setText(currentRunner.getLastSplit());
                Log.d("Splits:", "total time: " + currentTime + " |split: " + splitForLog);
            }
        }
    }

    public void resetRunners() {
        RunnerTime currentRunner;
        for (int i = 0; i < mTimers.size(); i++) {
            currentRunner = mTimers.get(i);
            currentRunner.setClockTime("00:00:00");
            currentRunner.setUpdatedTime(0);
            currentRunner.setClockStopped(false);
            currentRunner.getmSplits().clear();currentRunner.getmTotalTimesSplits().clear();// clear the splits
            currentRunner.setLastSplit("00:00.000");
            currentRunner.getLastSplitsButton().setText(currentRunner.getLastSplit());
        }
    }

    public class TimerView extends RecyclerView.ViewHolder {
        private TextView nameView;
        private Button splitButton;
        private Button stopButton;
        private TextView runnerTime;
        private Button splitsReviewButton;

        public TimerView(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.runner_name);
            runnerTime = (TextView) itemView.findViewById(R.id.runner_time);
            splitButton = (Button) itemView.findViewById(R.id.split);
            stopButton = (Button) itemView.findViewById(R.id.stop);
            splitsReviewButton = (Button) itemView.findViewById(R.id.review_splits);
        }
    }

    public interface Callback {
        public void onEdit(RunnerTime timer);

        void launchReviewDialogCallback(RunnerTime mCurrentTimer);
    }
}
