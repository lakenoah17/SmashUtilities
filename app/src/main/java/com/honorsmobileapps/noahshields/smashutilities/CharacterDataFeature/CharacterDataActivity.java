package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharacterDataActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<CharacterData>{

    //KuroganeHammer API URL. Used to retrieve character data
    private static final String BASIC_CHARACTER_URL = "https://api.kuroganehammer.com/api/characters?game=ultimate";

    //Loader id
    private static final int CHARACTER_DATA_LOADER_ID = 1;

    //Extra keys
    private static final String EXTRA_CHARACTER_POSITION = "characterPos";
    private static final String EXTRA_CHARACTER_NAME = "characterName";
    private static final String EXTRA_PICTURE_URL = "characterPicture";

    //Fields
    private int charPos;
    private String charName;
    private String picURL;
    private int indexOfAttack = 0;
    private ArrayList<AttackData> attacks;
    private ProgressBar progressBar;
    private TextView noCharacters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_data);

        //Retrieving the different extra values
        charPos = this.getIntent().getIntExtra(EXTRA_CHARACTER_POSITION, 0);
        charName = this.getIntent().getStringExtra(EXTRA_CHARACTER_NAME);
        picURL = this.getIntent().getStringExtra(EXTRA_PICTURE_URL);

        //Loading spinner. Gives a visual queue to the user that the page is still loading
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner_characterData);
        noCharacters = (TextView) findViewById(R.id.TextView_NoDataFound);

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //Making sure the user has internet
        if (networkInfo != null && networkInfo.isConnected()){
            getSupportLoaderManager().initLoader(CHARACTER_DATA_LOADER_ID,null,this);
        }
        //Notifies the user that they can't connect to the API
        else{
            progressBar.setVisibility(View.GONE);
            noCharacters.setText("No internet connection");
            noCharacters.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<CharacterData> onCreateLoader(int i, Bundle bundle){
        return new CharacterDataLoader(this, BASIC_CHARACTER_URL, charPos);
    }


    @Override
    public void onLoadFinished(Loader<CharacterData> loader, CharacterData characterData) {

        //Makes the loader visual effect invisible
        progressBar.setVisibility(View.GONE);

        //Making sure that the characterData was returned
        if (characterData != null){

            //Sets the name text
            TextView name = (TextView) findViewById(R.id.CharacterName);
            name.setText(charName);

            //Loading the main image
            Picasso.get().load(Uri.parse(picURL)).placeholder(R.drawable.ic_launcher_background).into((ImageView) findViewById(R.id.CharacterDataImage));

            //Gets the all of the values of the character data
            String[] movement = characterData.getCharacterMovement().getMovementArray();

            //Looping through and filling out character data chart
            int pos = 0;

            //Loops through the 9 rows
            for (int row = 1; row <= 9; row++){

                //Populates every other column of the movement chart
                for (int col = 1; col <= 3; col+=2){
                    String gridLocation = "TextView_MovementGrid_R" + row + "_C"+col;

                    //Setting the values to the text chart at correct positions
                    TextView textView = (TextView) findViewById(this.getResources().getIdentifier(gridLocation, "id", this.getPackageName()));
                    textView.setText(movement[pos]);
                    pos++;
                }
            }

            attacks = characterData.getAttacks();

            //Populating the three different attack charts
            populateGridAttackLayout("ground");
            populateGridAttackLayout("aerial");
            populateGridAttackLayout("special");

            LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout_CharacterDataLayout);
            layout.setVisibility(View.VISIBLE);
        }
        //This is used when there is an error in finding the character data
        else {
            LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout_CharacterDataLayout);
            layout.setVisibility(View.GONE);
            noCharacters.setText("No Characters Found");
            noCharacters.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onLoaderReset(Loader<CharacterData> loader) {
        //Empty
    }

    //Allows for dynamic creation of Intent instead of hardcoding Extra keys into two classes
    public static Intent newIntent(Context packageContext, int characterPos, String name, String picUrl){
        Intent startNew = new Intent(packageContext, CharacterDataActivity.class);
        startNew.putExtra(EXTRA_CHARACTER_POSITION,characterPos);
        startNew.putExtra(EXTRA_CHARACTER_NAME, name);
        startNew.putExtra(EXTRA_PICTURE_URL,picUrl);
        return startNew;
    }

    //Populates the different attacks properties into the grid
    public void populateGridAttackLayout(String moveType){



        //Used to retrieve the currAttackGrid
        String gridName = "GridLayout_" + moveType + "AttackGrid";

        //Retrieves the appropriate GridLayout
        GridLayout currAttackGrid = (GridLayout) findViewById(this.getResources().getIdentifier(gridName, "id", this.getPackageName()));

        //Row always starts at the second row because the first row is the value's name
        int row = 1;

        //Looping through all of the different attacks
        while(!attacks.isEmpty() && attacks.get(indexOfAttack).getMoveType().equals(moveType)) {
            int col = 0;

            //Looping through all of the values of the current attack
            for (String attackValue : attacks.get(indexOfAttack).getValues()) {
                TextView data = (TextView) getLayoutInflater().inflate(R.layout.text_view_grid_cell_template, null);

                //It is necessary to create a new object every time
                GridLayout.LayoutParams gridLayoutParameters = new GridLayout.LayoutParams();
                gridLayoutParameters.leftMargin = 3;
                gridLayoutParameters.rightMargin = 3;
                gridLayoutParameters.topMargin = 3;
                gridLayoutParameters.bottomMargin = 3;
                gridLayoutParameters.setGravity(Gravity.CENTER | Gravity.FILL);

                //Sets where the data is in the grid
                gridLayoutParameters.columnSpec = GridLayout.spec(col,1,1f);
                gridLayoutParameters.rowSpec = GridLayout.spec(row,1,1f);

                //TODO: Remove Weight Dependant Field. Reason: Unnecessary
                //Skips all null values retrieved from the API. Also skips the weight dependant column
                if (!attackValue.equals("null") && !attackValue.equals(moveType)) {
                    attackValue = " " + attackValue + " ";
                    data.setText(attackValue);
                    currAttackGrid.addView(data, gridLayoutParameters);
                    col++;
                }
            }
            row++;
            currAttackGrid.setRowCount(row+1);
            indexOfAttack++;
        }
    }
}
