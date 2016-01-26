package edu.rosehulman.milnerml.multipletimers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
//import android.support.design.widget.FloatingActionButton;

/**
 * Created by kumarms on 1/25/2016.
 */
public class TimingActivity extends AppCompatActivity implements TimerAdapter.Callback{
    private TimerAdapter mTimerAdapter;
    private RecyclerView mRecyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timers_layout);

        mTimerAdapter = new TimerAdapter(this,(TimerAdapter.Callback)this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycer_view);
        mRecyclerView.setAdapter(mTimerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);// it isn't importing properly
        Button addingButton = (Button) findViewById(R.id.adding_button);
        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditDialog(null);
            }
        });
    }
    public void showAddEditDialog(final Timer timer) {
        DialogFragment df = new DialogFragment() {
            public Dialog onCreateDialog(Bundle savedInstanceState) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(timer == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
                View view = getActivity().getLayoutInflater().inflate(R.layout.adding_runner_dialog, null, false);
                builder.setView(view);
                final EditText runnerName = (EditText) view.findViewById(R.id.editText);
                if (timer != null) {
                    // pre-populate
                    runnerName.setText(timer.getText());

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
                            mTimerAdapter.add(new Timer(name));
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
    public void onEdit(Timer timer) {
        showAddEditDialog(timer);
    }
}
