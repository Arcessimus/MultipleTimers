package edu.rosehulman.milnerml.multipletimers;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by theoderic on 1/31/16.
 */
public class RaceStorage {

    ArrayList<File> fileList;
    Context context;

    public RaceStorage(Context thisContext)
    {
        this.fileList = new ArrayList<>();
        this.context = thisContext;
    }

    public void saveRace(RunnerTime r)
    {
        //serialize into JSON

        //open file
        //File file = new File(this.context.getFilesDir(), name);

        //write JSON to file

        //add file to arraylist
        //this.fileList.add(file);

        //close file
    }

    public void loadRace(File r)
    {
        //open file

        //extract JSON from file

        //deserialize JSON into RunnerTime object
    }

    public void deleteRace()
    {
        //delete file

        //delete RunnerTime object from list
    }

    public File getFileatPos(int pos)
    {
        return fileList.get(pos);
    }
}
