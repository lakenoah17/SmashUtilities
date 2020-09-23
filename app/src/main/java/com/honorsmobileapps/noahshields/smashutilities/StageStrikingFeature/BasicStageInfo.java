package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

//This is a data class that stores info about a stage
//for the Stage Striking feature
public class BasicStageInfo {
    private int imageResourceID;
    private String stageName;
    private boolean isSelected;

    //Constructs the Class
    //Params:
    //  imageResourceID - the image resource to access for this stage
    //  stageName - the name of the stage
    //  isSelected - this variable determines whether this stage has
    //               been previously selected by the user
    public BasicStageInfo(int imageResourceID, String stageName, boolean isSelected){
        this.imageResourceID = imageResourceID;
        this.stageName = stageName;
        this.isSelected = isSelected;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public String getStageName() {
        return stageName;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean getSelected(){
        return isSelected;
    }
}
