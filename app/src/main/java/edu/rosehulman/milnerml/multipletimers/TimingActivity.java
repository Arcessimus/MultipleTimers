package edu.rosehulman.milnerml.multipletimers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by kumarms on 1/25/2016.
 */
public class TimingActivity extends AppCompatActivity implements TimerAdapter.Callback {
    private TimerAdapter mTimerAdapter;
    private RecyclerView mRecyclerView;

    public TextView time;
    public long starttime = 0L;
    public long timeInMilliseconds = 0L;
    public long timeSwapBuff = 0L;
    public long updatedtime = 0L;
    public int startpause = 1;
    public int secs = 0;
    public int mins = 0;
    public int milliseconds = 0;
    Handler handler = new Handler();
    private boolean stoprestart;
    private ArrayList<Parcelable> runnerCards = new ArrayList<>();
    private int choiceSetting = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        int choiceTemp = sharedPreferences.getInt(getString(R.string.choice), 1);
        if (choiceTemp != 0){
            choiceSetting = choiceTemp;
        }
        setContentView(R.layout.timers_layout);

        mTimerAdapter = new TimerAdapter(this,(TimerAdapter.Callback)this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycer_view);
        mRecyclerView.setAdapter(mTimerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);// it isn'startpause importing properly
        Button addingButton = (Button) findViewById(R.id.adding_button);
        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditDialog(null);
            }
        });

        time = (TextView) findViewById(R.id.main_time);
        final Button butnstart = (Button) findViewById(R.id.main_start);
        final Button butnstop = (Button) findViewById(R.id.main_stop);

        butnstart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (startpause == 1) {
                    butnstart.setText("Pause");
                    butnstop.setText("Stop");
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    startpause = 0;
                    stoprestart = false;
                } else {
                    butnstart.setText("Start");
                    butnstop.setText("Restart");
                    time.setTextColor(Color.RED);
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    startpause = 1;
                    stoprestart = true;
                }}
        });

        butnstop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (stoprestart){// if you press restart then reset all values to their original states
                    starttime = 0L;
                    timeInMilliseconds = 0L;
                    timeSwapBuff = 0L;
                    updatedtime = 0L;
                    startpause = 1;
                    secs = 0;
                    mins = 0;
                    milliseconds = 0;
                    butnstart.setText("Start");
                    butnstop.setText("Restart");
                    handler.removeCallbacks(updateTimer);
                    time.setText("00:00:00");
                    stoprestart = false;
                    mTimerAdapter.resetRunners();
                }else{            // if the clock is going it should just stop
                    butnstart.setText("Start");
                    butnstop.setText("Restart");
                    time.setTextColor(Color.RED);
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    startpause = 1;
                    stoprestart = true;
                }
            }});
        if (savedInstanceState != null){
            runnerCards = savedInstanceState.getParcelableArrayList("mRunners");
            int count = savedInstanceState.getInt("count");
            for (int i = 0; i<count; i++){
                mTimerAdapter.add((RunnerTime) runnerCards.get(i));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int count = mTimerAdapter.getmTimers().size();
        for (int i = 0; i<count; i++){
            runnerCards.add(mTimerAdapter.getmTimers().get(i));
        }
        outState.putParcelableArrayList("mRunners", runnerCards);
        outState.putInt("count", count);
        // Save off data using outState.putXX(key, value)
        // Hint: you will use the appropriate methods to store int[] and ints,
        // maybe a String.
    }


    public Runnable updateTimer = new Runnable() {
        public void run() {

            timeInMilliseconds = (int) (SystemClock.uptimeMillis() - starttime);

            updatedtime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);
            String currentTime = "" + mins + ":" + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds);
            time.setText(currentTime);
            time.setTextColor(Color.WHITE);
            handler.postDelayed(this, 0);
            mTimerAdapter.updateRunnerCards(currentTime,updatedtime);
        }

    };


    public void showAddEditDialog(final RunnerTime timer) {
        DialogFragment df = new DialogFragment() {
            public Dialog onCreateDialog(Bundle savedInstanceState) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(timer == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
                View view = getActivity().getLayoutInflater().inflate(R.layout.adding_runner_dialog, null, false);
                builder.setView(view);
                final EditText runnerName = (EditText) view.findViewById(R.id.editText);
                final TextView runnerTime = (TextView)findViewById(R.id.runner_time);
                if (timer != null) {
                    // pre-populate
                    runnerName.setText(timer.getName());

                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // empty
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // empty
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            String quote = runnerName.getText().toString();
                            mTimerAdapter.update(timer, quote);
                        }
                    };

                    runnerName.addTextChangedListener(textWatcher);
                }

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (timer == null) {
                            String name = runnerName.getText().toString();
                            mTimerAdapter.add(new RunnerTime(name));
                        }
                    }
                });
                builder.setNeutralButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTimerAdapter.remove(timer);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);

                return builder.create();
            }
        };
        df.show(getSupportFragmentManager(), "add");
    }

    @Override
    public void onEdit(RunnerTime timer) {
        showAddEditDialog(timer);
    }

    @Override
    public void launchReviewDialogCallback(RunnerTime mCurrentTimer) {
        launchReviewDialog(mCurrentTimer,choiceSetting);
    }


    public class CounterClass extends Timer {

    }
    private void launchReviewDialog(final RunnerTime runner, final int choice) {
        DialogFragment df = new DialogFragment() {
            public Dialog onCreateDialog(Bundle savedInstanceState) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(runner.getName() + "'s Splits");
                View view = getActivity().getLayoutInflater().inflate(R.layout.splits_dialog_layout, null, false);
                builder.setView(view);
                final TextView lap = (TextView) view.findViewById(R.id.runner_name_for_dialog);
                lap.setText(R.string.lap_number);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.splits_recycler_view);
                SplitsAdapter adapter = new SplitsAdapter(choice);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                for (int i = 0; i < runner.getmSplits().size(); i++){
                    adapter.addSplits(runner.getmSplits().get(i),runner.getmTotalTimesSplits().get(i));
                }
//                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (timer == null) {
//                            String name = runnerName.getText().toString();
//                            mTimerAdapter.add(new RunnerTime(name));
//                        }
//                    }
//                });
//                builder.setNeutralButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        mTimerAdapter.remove(timer);
//                    }
//                });
                builder.setNegativeButton(android.R.string.ok, null);// just need one button to exit

                return builder.create();
            }
        };
        df.show(getSupportFragmentManager(), "add");
    }
}
