package edu.rosehulman.milnerml.multipletimers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

//import javax.security.auth.callback.Callback;

/**
 * Created by kumarms on 1/25/2016.
 */
public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerView> {
    private ArrayList<Timer> mTimers = new ArrayList<>();
    private Context mContext;
    private Callback mCallBack;

    public TimerAdapter() {
    }

    public TimerAdapter(Context mContext, Callback callback) {
        this.mContext = mContext;
        this.mCallBack = callback;
    }

    public void add(Timer timer){
        mTimers.add(timer);
    }

    @Override
    public TimerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.runner_card,parent,false);
        return new TimerView(view);
    }

    @Override
    public void onBindViewHolder(TimerAdapter.TimerView holder, int position) {
        final Timer mCurrentTimer = mTimers.get(position);
        holder.nameView.setText(mCurrentTimer.getText());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
        public boolean onLongClick(View v){
                mCallBack.onEdit(mCurrentTimer);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimers.size();
    }

    public void update(Timer timer, String name) {
        timer.setName(name);
        notifyDataSetChanged();
    }

    public void remove(Timer timer) {
        mTimers.remove(timer);
    }

    public class TimerView extends RecyclerView.ViewHolder {
        private TextView nameView;
        public TimerView(View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.runner_name);
        }
    }
    public interface Callback {
        public void onEdit (Timer timer);
    }
}
