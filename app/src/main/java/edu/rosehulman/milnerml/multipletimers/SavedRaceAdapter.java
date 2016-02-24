package edu.rosehulman.milnerml.multipletimers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theoderic on 1/27/16.
 */
public class SavedRaceAdapter extends RecyclerView.Adapter<SavedRaceAdapter.ViewHolder> {

    public static final String race_path = "https://multipletimers.firebaseio.com/races";
    private List<Race> races;
    //private Callback mCallback;
    private Firebase mRacesRef;

    public SavedRaceAdapter(Context context)
    {
        //mCallback = callback;
        races = new ArrayList<>();
        Firebase.setAndroidContext(context);
        mRacesRef = new Firebase(race_path);
        mRacesRef.addChildEventListener(new SavedRaceChildEventListener());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_races, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSaveName.setText(races.get(position).getName());
        holder.mEditButton.setText(R.string.edit_button_text);
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

    private class SavedRaceChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Race race = dataSnapshot.getValue(Race.class);
            race.setKey(dataSnapshot.getKey());
            races.add(0,race);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Race race = dataSnapshot.getValue(Race.class);
            String key = dataSnapshot.getKey();

            for(Race r : races) {
                if(r.getKey().equals(key)) {
                    r.setName(race.getName());
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();

            for(Race r : races) {
                if(r.getKey().equals(key)) {
                    races.remove(r);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //no moving of items
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.e("Save", firebaseError.getMessage());
        }
    }
}
