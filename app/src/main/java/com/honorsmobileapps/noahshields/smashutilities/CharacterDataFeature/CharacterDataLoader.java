package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.honorsmobileapps.noahshields.smashutilities.QueryUtils;

public class CharacterDataLoader extends AsyncTaskLoader<CharacterData> {
    private String characterInfo;
    private int charPos;

    public CharacterDataLoader(Context context, String characterInfo, int charPos){
        super(context);
        this.characterInfo = characterInfo;
        this.charPos = charPos;
    }
    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public CharacterData loadInBackground(){
        if(characterInfo == null){
            return null;
        }
        return QueryUtils.fetchCharacterData(characterInfo,charPos);
    }
}
