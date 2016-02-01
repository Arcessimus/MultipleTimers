package edu.rosehulman.milnerml.multipletimers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public TimerAdapter() {
    }

    public TimerAdapter(Context mContext, Callback callback) {
        this.mContext = mContext;
        this.mCallBack = callback;
    }

    public void add(RunnerTime timer){
        mTimers.add(timer);
    }

    @Override
    public TimerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.runner_card,parent,false);
        return new TimerView(view);
    }

    @Override
    public void onBindViewHolder(TimerAdapter.TimerView holder, int position) {
        final RunnerTime mCurrentTimer = mTimers.get(position);
        holder.nameView.setText(mCurrentTimer.getText());
        mCurrentTimer.setClock(holder.runnerTime);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCallBack.onEdit(mCurrentTimer);
                return true;
            }
        });
        holder.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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

    public void updateRunnerCards(String currentTime) {
        for (int i = 0; i<mTimers.size(); i++){
            mTimers.get(i).setClockTime(currentTime);
        }
    }

    public class TimerView extends RecyclerView.ViewHolder {
        private TextView nameView;
        private Button splitButton;
        private Button stopButton;
        private TextView runnerTime;
        public TimerView(View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.runner_name);
            runnerTime = (TextView)itemView.findViewById(R.id.runner_time);
            splitButton = (Button)itemView.findViewById(R.id.split);
            stopButton = (Button)itemView.findViewById(R.id.stop);
        }
    }
    public interface Callback {
        public void onEdit (RunnerTime timer);
    }
}
