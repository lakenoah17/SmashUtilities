package com.honorsmobileapps.noahshields.smashutilities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature.CharacterDataSelectionActivity;
import com.honorsmobileapps.noahshields.smashutilities.Notes.AccessNotesActivity;
import com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature.ChooseStageListActivity;

//This activity is where the user can choose which feature they want to use
public class MainStartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        //Different navigation card views get set up
        CardView cardView = (CardView) findViewById(R.id.CardView_SelectCharacterData);
        CardView cardView1 = (CardView) findViewById(R.id.CardView_CreateTournament);
        CardView cardView2 = (CardView)  findViewById(R.id.CardView_SelectStageStriking);
        CardView cardView3 = (CardView) findViewById(R.id.CardView_Notes);


        View.OnClickListener clickListener = new View.OnClickListener() {
            //Starts an activity based on which CardView was clicked
            //Params:
            //  v - the view that was clicked
            @Override
            public void onClick(View v) {
                //Currently not in use. Tournament feature needs to be implemented
                if (v.getId() == R.id.CardView_CreateTournament) {
//                    Intent startNext = new Intent(v.getContext(), ChooseBetweenTournamentFeaturesActivity.class);
//                    startActivity(startNext);
                }
                //Brings user to CharacterDataSelectionActivity
                else if(v.getId() == R.id.CardView_SelectCharacterData){
                    Intent startNext = new Intent(v.getContext(), CharacterDataSelectionActivity.class);
                    startActivity(startNext);
                }
                //Brings user to ChooseStageListActivity
                else if(v.getId() == R.id.CardView_SelectStageStriking){
                    Intent startNext = new Intent(v.getContext(), ChooseStageListActivity.class);
                    startActivity(startNext);
                }
                //Brings user to AccessNotesActivity
                else if(v.getId() == R.id.CardView_Notes){
                    Intent startNext = new Intent(v.getContext(), AccessNotesActivity.class);
                    startActivity(startNext);
                }
            }
        };
        cardView.setOnClickListener(clickListener);
        cardView2.setOnClickListener(clickListener);
        cardView3.setOnClickListener(clickListener);
    }
}
