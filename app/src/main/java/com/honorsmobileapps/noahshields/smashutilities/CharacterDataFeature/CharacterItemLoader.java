package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.honorsmobileapps.noahshields.smashutilities.QueryUtils;

import java.util.ArrayList;

public class CharacterItemLoader extends AsyncTaskLoader<ArrayList<BasicCharacterInfo>> {
    private String requestUrl;

    public CharacterItemLoader(Context context, String url){
        super(context);
        requestUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public ArrayList<BasicCharacterInfo> loadInBackground(){
        if(requestUrl == null){
            return null;
        }
        return QueryUtils.fetchBasicCharacterInfo(requestUrl);
    }
}
