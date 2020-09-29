package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

//Data class to hold info for stage items
public class StageListInfo {
    private String name;
    private String key;

    //Constructs a StageListInfo object
    //Params:
    //  name - the name of the stage list
    //  key - the key of the stage list in Shared Preferences
    public StageListInfo(String name, String key){
        this.name = name;
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public String getKey() {
        return key;
    }
}
