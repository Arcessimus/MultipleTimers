package edu.rosehulman.milnerml.multipletimers;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by theoderic on 1/31/16.
 */
public class RaceStorage {

    ArrayList<RunnerTime> runnerTimes;
    File racesFile;
    Context context;

    public RaceStorage(Context thisContext)
    {
        this.runnerTimes = new ArrayList<>();
        this.context = thisContext;
        this.racesFile = new File(this.context.getFilesDir(), "Races.txt");
    }

    public void saveRace(RunnerTime r)
    {
        //add to list
        this.runnerTimes.add(r);
        //refresh file
        refreshFile();

    }

    public void loadRaces()
    {
        //read from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(this.racesFile));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //extract JSON from file

        //close file

        //deserialize JSON into list of RunnerTime objects
    }

    public void deleteRace(int pos)
    {
        //remove at position
        this.runnerTimes.remove(pos);
        //refresh file
        refreshFile();
    }

    public void deleteRace(RunnerTime r)
    {
        //remove the selected object
        this.runnerTimes.remove(r);
        //refresh file
        refreshFile();
    }

    private void refreshFile()
    {
        //serialize list into JSON

        //open file

        //clear file

        //write JSON list to file

        //close file
    }
}
