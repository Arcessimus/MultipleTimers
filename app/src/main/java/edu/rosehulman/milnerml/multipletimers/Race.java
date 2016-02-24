package edu.rosehulman.milnerml.multipletimers;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by theoderic on 2/24/16.
 */
public class Race {

    private String name;
    @JsonIgnore
    private String key;

    public Race() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



}
