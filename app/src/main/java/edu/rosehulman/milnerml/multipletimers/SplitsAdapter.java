package edu.rosehulman.milnerml.multipletimers;

import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kumarms on 2/8/2016.
 */
public class SplitsAdapter extends RecyclerView.Adapter<SplitsAdapter.SplitView> {
    private ArrayList<Split> mSplits;
    private int choice;
    public SplitsAdapter(int choice) {
        this.mSplits = new ArrayList<>();
        this.choice = choice;
    }

    @Override
    public SplitView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.split_layout, parent, false);
        return new SplitView(view);
    }

    @Override
    public void onBindViewHolder(SplitView holder, int position) {
        Split split = mSplits.get(position);
        if (choice == 1){
            holder.splits.setText(split.getSplit());
            holder.totalSplits.setText(split.getTotal());
            holder.splitNumber.setText("" + position);
        }else if(choice == 2) {// only splits
            holder.totalSplits.setText("");
            holder.totalText.setText("");
            holder.splits.setText(split.getSplit());
            holder.splitNumber.setText("" + position);
        }else if(choice == 3) {// only totals
            holder.splits.setText("");
            holder.splitsText.setText("");
            holder.totalSplits.setText(split.getTotal());
            holder.splitNumber.setText("" + position);
        }

    }

    @Override
    public int getItemCount() {
        return mSplits.size();
    }

    public void addSplits(String currentTime, String currentTotalTime) {
        mSplits.add(new Split(currentTime,currentTotalTime));
        Log.d("splits:", "made it to adapter");
        notifyDataSetChanged();
    }

    public class SplitView extends RecyclerView.ViewHolder {
        TextView splits;
        TextView totalSplits;
        TextView totalText;
        TextView splitsText;
        TextView splitNumber;
        public SplitView(View itemView) {
            super(itemView);
            splits = (TextView) itemView.findViewById(R.id.split_time_split);
            totalSplits = (TextView) itemView.findViewById(R.id.split_time_total);
            totalText = (TextView) itemView.findViewById(R.id.total_split);
            splitsText = (TextView) itemView.findViewById(R.id.split_split);
            splitNumber = (TextView) itemView.findViewById(R.id.split_number);
        }
    }
}
