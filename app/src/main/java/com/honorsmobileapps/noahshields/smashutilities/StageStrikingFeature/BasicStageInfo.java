package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

public class BasicStageInfo {
    private int imageResourceID;
    private String stageName;
    private boolean isSelected;
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
