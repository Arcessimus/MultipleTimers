package edu.rosehulman.milnerml.multipletimers;

/**
 * Created by kumarms on 2/9/2016.
 */
public class Split {
    private String split;
    private String total;

    public Split(String split, String total) {
        this.split = split;
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSplit() {

        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }
}
