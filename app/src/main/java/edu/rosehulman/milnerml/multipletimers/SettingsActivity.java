package edu.rosehulman.milnerml.multipletimers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by kumarms on 1/25/2016.
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.settings_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
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
        Log.d("choice",": " + choice);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.choice), choice);
        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //decide later, possibly do nothing
    }
}
