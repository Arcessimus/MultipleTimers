package edu.rosehulman.milnerml.multipletimers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by kumarms on 1/25/2016.
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private int officialChoice = 0;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.settings_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        Button saveButton = (Button)findViewById(R.id.save_settings);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonAction();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //find a way to make this call the confirmation dialog and then change the settings
        Log.d("selected", ""+id);
        int choice = 1;
        switch ((int)id){
            case 0:
                choice = 1;
                break;
            case 1:
                choice = 2;
                break;
            case 2:
                choice = 3;
                break;
        }
        Log.d("selected",": " + choice);
//        SharedPreferences sharedPref = this.getSharedPreferences("Prefs",Context.MODE_PRIVATE); // moved to a seperate method to account for save button.
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt("KEY", choice);
//        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //decide later, possibly do nothing
    }

    public void saveButtonAction (){
        DialogFragment df = new DialogFragment() {
            public Dialog onCreateDialog(Bundle savedInstanceState) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getActivity().getLayoutInflater().inflate(R.layout.are_you_sure_dialog, null, false);
                builder.setView(view);
                builder.setTitle(getString(R.string.are_you_sure));
//                final Button yesButton = (Button) view.findViewById(R.id.confirmation_button);
//                yesButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences sharedPref = getContext().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putInt("KEY", officialChoice);
//                        editor.commit();
//                    }
//                });
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPref = getContext().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("KEY", officialChoice);
                        editor.commit();
                    }
                });
//                builder.setNeutralButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        mTimerAdapter.remove(timer);
//                    }
//                });
                builder.setNegativeButton(android.R.string.no, null);// just need one button to exit

                return builder.create();
            }
        };
        df.show(getSupportFragmentManager(), "add");
    }
}
