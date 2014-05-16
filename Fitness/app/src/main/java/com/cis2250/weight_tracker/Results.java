package com.cis2250.weight_tracker;

/**
 * Created by Normandy SR3 on 2/4/14.
 */
public class Results
{
    private String staWeight;
    private String curWeight;
    private String gWeight;
    private String curWeightProg;
    private String gWeightProg;


    public Results(){}

    public Results (String staWeight, String curWeight, String gWeight, String curWeightProg, String gWeightProg)
    {
        super();
        this.staWeight = staWeight;
        this.curWeight = curWeight;
        this.gWeight = gWeight;
        this.curWeightProg = curWeightProg;
        this.gWeightProg = gWeightProg;

    }

    public String getStaWeight() {
        return staWeight;
    }

    public void setStaWeight(String staWeight) {
        this.staWeight = staWeight;
    }

    public String getCurWeight() {
        return curWeight;
    }

    public void setCurWeight(String curWeight) {
        this.curWeight = curWeight;
    }

    public String getGWeight() {
        return gWeight;
    }

    public void setGWeight(String gWeight) {
        this.gWeight = gWeight;
    }

    public String getCurWeightProg() {
        return curWeightProg;
    }

    public void setCurWeightProg(String curWeightProg) {
        this.curWeightProg = curWeightProg;
    }

    public String getgWeightProg() {
        return gWeightProg;
    }

    public void setgWeightProg(String gWeightProg) {
        this.gWeightProg = gWeightProg;
    }

    @Override
    public String toString()
    {
        return "Starting Weight: " + staWeight + "\nCurrent Weight:  " + curWeight +
                " \nGoal Weight " + gWeight + "\nProgress From Last Current Weight Entry: " +
                curWeightProg + "\nGoal Weight Progress: " + gWeightProg;
    }


}
