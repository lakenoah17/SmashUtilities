package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;

import java.util.ArrayList;

//This activity allows the user to pick which characters data
//they would like to see. The character image name and color
//are retrieved from KuroganeHammer API
public class CharacterDataSelectionActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<BasicCharacterInfo>>{
    //KuroganeHammer API URL
    private static final String BASIC_CHARACTER_URL = "https://api.kuroganehammer.com/api/characters?game=ultimate";

    //Loader ID
    private static final int BASIC_CHARACTER_INFO_LOADER_ID = 2;

    //Fields
    private RecyclerView characterRecycler;
    private CharacterItemAdapter characterItemAdapter;
    private ProgressBar progressBar;
    private TextView noCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_data_selection);

        //Setting up all instance variables
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        noCharacters = (TextView) findViewById(R.id.TextView_NoCharactersFound);
        noCharacters.setVisibility(View.GONE);
        characterRecycler = findViewById(R.id.RecyclerView_CharacterSelection);
        characterItemAdapter = new CharacterItemAdapter(new ArrayList<BasicCharacterInfo>());
        characterRecycler.setAdapter(characterItemAdapter);

        //This is to adapt RecyclerView column count based around screen size
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();

        //Gets the screen metrics
        display.getMetrics(outMetrics);

        //Gets the pixel density and the screen width
        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;

        //Sets up the manager to use for the RecyclerView
        GridLayoutManager gridLayoutManager = (GridLayoutManager)characterRecycler.getLayoutManager();
        gridLayoutManager.setSpanCount(((int)dpWidth)/256);

        //Checks if device is online
        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //If the device isn't online the text No internet connection is made visible
        if (networkInfo != null && networkInfo.isConnected()){
            getSupportLoaderManager().initLoader(BASIC_CHARACTER_INFO_LOADER_ID,null,this);
        }
        else{
            progressBar.setVisibility(View.GONE);
            noCharacters.setText("No internet connection");
            noCharacters.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public Loader<ArrayList<BasicCharacterInfo>> onCreateLoader(int i, Bundle bundle){
        return new CharacterItemLoader(this, BASIC_CHARACTER_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BasicCharacterInfo>> loader, ArrayList<BasicCharacterInfo> characters) {
        progressBar.setVisibility(View.GONE);

        //Checking to make sure that there wasn't a timeout on loading
        if (characters != null && !characters.isEmpty()){
            characterItemAdapter.setCharacters(characters);
            characterItemAdapter.notifyDataSetChanged();
            characterRecycler.setVisibility(View.VISIBLE);
        }
        else {
            characterRecycler.setVisibility(View.GONE);
            noCharacters.setText("No Characters Found");
            noCharacters.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<BasicCharacterInfo>> loader) {
        characterItemAdapter.setCharacters(new ArrayList<BasicCharacterInfo>());
    }

}
