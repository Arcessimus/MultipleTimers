package edu.rosehulman.milnerml.multipletimers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by theoderic on 1/27/16.
 */
public class SavedRaceAdapter extends RecyclerView.Adapter<SavedRaceAdapter.ViewHolder> {


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
}
