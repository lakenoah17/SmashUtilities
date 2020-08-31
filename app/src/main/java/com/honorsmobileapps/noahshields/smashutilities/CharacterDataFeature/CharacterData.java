package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import java.util.ArrayList;

//This is a data class that is used to store all of the necessary data for a character that the user selects.
//TODO: cache this object to minimize the use of accessing the API
public class CharacterData {

    //Fields
    private MovementValData characterMovement;
    private ArrayList<AttackData> attacks = new ArrayList<AttackData>();

    //Allows for the creation of an empty CharacterData
    public CharacterData(){
        characterMovement = new MovementValData();
    }

    //Initializes the fields of the CharacterData object
    //Params:
    //  movementValData - Contains all of the movement values of the character
    //  attacks - an ArrayList of all of the attacks the the character has
    public CharacterData(MovementValData movementValData, ArrayList<AttackData> attacks){
        this.characterMovement = movementValData;
        this.attacks = attacks;
    }

    public MovementValData getCharacterMovement() {
        return characterMovement;
    }

    public ArrayList<AttackData> getAttacks() {
        return attacks;
    }
}
