package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.honorsmobileapps.noahshields.smashutilities.QueryUtils;


//Creates an Async task to access the KuroganeHammer API to
//retrieve all info needed for a CharacterData object
public class CharacterDataLoader extends AsyncTaskLoader<CharacterData> {
    //Fields
    private String characterURL;
    private int characterIndex;

    //Initialises all of the fields
    //Params:
    //  context - The context of the activity using this loader
    //  characterURL - a string representation of the URL to access the character at
    //  characterIndex - the index of the character in the JSON array of data retrieved from KuroganeHammer
    public CharacterDataLoader(Context context, String characterURL, int characterIndex){
        super(context);
        this.characterURL = characterURL;
        this.characterIndex = characterIndex;
    }

    //TODO: Look up forceLoad()
    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    //Retrieves and returns all a character data object utilising KuroganeHammer
    @Override
    public CharacterData loadInBackground(){
        //Makes sure URL isn't null
        if(characterURL == null){
            return null;
        }

        //Returns a CharacterData object
        return QueryUtils.fetchCharacterData(characterURL, characterIndex);
    }
}
