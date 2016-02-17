package edu.rosehulman.milnerml.multipletimers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//import javax.security.auth.callback.Callback;

/**
 * Created by kumarms on 1/25/2016.
 */
public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerView> {
    private ArrayList<RunnerTime> mTimers = new ArrayList<>();
    private Context mContext;
    private Callback mCallBack;

    public ArrayList<RunnerTime> getmTimers() {
        return mTimers;
    }

    public TimerAdapter() {
    }

    public TimerAdapter(Context mContext, Callback callback) {
        this.mContext = mContext;
        this.mCallBack = callback;
    }

    public void add(RunnerTime timer) {
        mTimers.add(timer);
    }

    @Override
    public TimerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.runner_card, parent, false);
        return new TimerView(view);
    }

    @Override
    public void onBindViewHolder(final TimerAdapter.TimerView holder, int position) {
        final RunnerTime mCurrentTimer = mTimers.get(position);
        holder.nameView.setText(mCurrentTimer.getName() + " ");
        mCurrentTimer.setClock(holder.runnerTime);
        mCurrentTimer.setLastSplitsButton(holder.splitsReviewButton);
//        holder.horizontalGridView.setAdapter(mCurrentTimer.getAdapter());
//        holder.horizontalGridView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.splitsReviewButton.setText(mCurrentTimer.getLastSplit());
        holder.splitsReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.launchReviewDialogCallback(mCurrentTimer);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {// on long click
            @Override
            public boolean onLongClick(View v) {
                mCallBack.onEdit(mCurrentTimer);
                return true;
            }
        });
        holder.stopButton.setOnClickListener(new View.OnClickListener() {// on stop button click
            @Override
            public void onClick(View v) {
                mCurrentTimer.setSplit(true);
                mCurrentTimer.setClockStopped(true);
            }
        });
        holder.splitButton.setOnClickListener(new View.OnClickListener() {// on split button click
            @Override
            public void onClick(View v) {
                mCurrentTimer.setSplit(true);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mTimers.size();
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
                Log.d("Splits:","total time: " + currentTime + " |split: " + splitForLog);
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
