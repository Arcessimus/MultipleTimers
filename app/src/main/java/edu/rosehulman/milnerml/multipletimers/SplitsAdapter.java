package edu.rosehulman.milnerml.multipletimers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kumarms on 2/8/2016.
 */
public class SplitsAdapter extends RecyclerView.Adapter<SplitsAdapter.SplitView> {
    private ArrayList<String> mSplits;
    private ArrayList<String> mTotalTimesSplits;

    public SplitsAdapter() {
        this.mSplits = new ArrayList<>();
        this.mTotalTimesSplits = new ArrayList<>();
    }

    @Override
    public SplitView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.split_layout, parent, false);
        return new SplitView(view);
    }

    @Override
    public void onBindViewHolder(SplitView holder, int position) {
        String split = mSplits.get(position);
        holder.splits.setText(split);
        String totalSplit = mTotalTimesSplits.get(position);
        holder.totalSplits.setText(totalSplit);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addSplits(String currentTime, String currentTotalTime) {
        mSplits.add(currentTime);
        mTotalTimesSplits.add(currentTotalTime);
    }

    public class SplitView extends RecyclerView.ViewHolder {
        TextView splits;
        TextView totalSplits;
        public SplitView(View itemView) {
            super(itemView);
            splits = (TextView) itemView.findViewById(R.id.split_time_split);
            totalSplits = (TextView) itemView.findViewById(R.id.split_time_total);
        }
    }
}
