package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

//Data class that represents very basic information about a character.
//Used for the RecyclerView to choose a character in the CharacterDataSelectionActivity
public class BasicCharacterInfo{

    //Fields
    private String name;
    private String color;
    private String picURL;

    //Initializes the fields of the BasicCharacterInfo Class
    public BasicCharacterInfo(String name, String color, String picURL){
        this.name = name;
        this.color = color;
        this.picURL = picURL;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getPicURL() {
        return picURL;
    }
}
