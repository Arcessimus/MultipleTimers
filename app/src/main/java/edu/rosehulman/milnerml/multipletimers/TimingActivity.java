package edu.rosehulman.milnerml.multipletimers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.DialogFragment;
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
public class TimingActivity extends AppCompatActivity {
    private TimerAdapter mTimerAdapter;
    private RecyclerView mRecyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timers_layout);

        mTimerAdapter = new TimerAdapter(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycer_view);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);// it isn't importing properly
        Button addingButton = (Button) findViewById(R.id.adding_button);
        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void showAddEditDialog(final WeatherPic weatherPic) {
        DialogFragment df = new DialogFragment() {
            public Dialog onCreateDialog(Bundle savedInstanceState) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(weatherPic == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
                View view = getActivity().getLayoutInflater().inflate(R.layout.addedit_element, null, false);
                builder.setView(view);
                final EditText captionEditText = (EditText) view.findViewById(R.id.dialog_add_weather_pic_text);
                final EditText URLEditText = (EditText) view.findViewById(R.id.dialog_add_url_text);
                if (weatherPic != null) {
                    // pre-populate
                    captionEditText.setText(weatherPic.getCaption());
                    URLEditText.setText(weatherPic.getUrl());

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
                            String quote = captionEditText.getText().toString();
                            String movie = URLEditText.getText().toString();
                            mAdapter.update(weatherPic, quote, movie);
                        }
                    };

                    captionEditText.addTextChangedListener(textWatcher);
                    URLEditText.addTextChangedListener(textWatcher);
                }

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (weatherPic == null) {
                            String caption = captionEditText.getText().toString();
                            String url = URLEditText.getText().toString();
                            mAdapter.add(new WeatherPic(caption, url));
                        }
                    }
                });
                builder.setNeutralButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.remove(weatherPic);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);

                return builder.create();
            }
        };
        df.show(getSupportFragmentManager(), "add");
    }
}
