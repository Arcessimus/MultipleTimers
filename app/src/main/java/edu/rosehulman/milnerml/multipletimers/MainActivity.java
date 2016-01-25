package edu.rosehulman.milnerml.multipletimers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        Button startTimingButton = (Button) findViewById(R.id.start_timing);
        startTimingButton.setOnClickListener(this);
        Button savedRacesButton = (Button) findViewById(R.id.saved_races);
        savedRacesButton.setOnClickListener(this);
        Button aboutButton = (Button) findViewById(R.id.about_tutorial);
        aboutButton.setOnClickListener(this);
        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.about_tutorial:
                intent = new Intent(this,AboutTutorialActivity.class);
                this.startActivity(intent);
                break;
            case R.id.saved_races:
                intent =  new Intent(this,SavedRacesActivity.class);
                this.startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(this,SettingsActivity.class);
                this.startActivity(intent);
                break;
            case R.id.start_timing:
                intent = new Intent(this,TimingActivity.class);
                this.startActivity(intent);
                break;
        }

    }
}
