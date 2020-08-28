package com.honorsmobileapps.noahshields.smashutilities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature.CharacterDataSelectionActivity;
import com.honorsmobileapps.noahshields.smashutilities.Notes.AccessNotesActivity;
import com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature.ChooseStageListActivity;

public class MainStartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        CardView cardView = (CardView) findViewById(R.id.CardView_SelectCharacterData);
        CardView cardView1 = (CardView) findViewById(R.id.CardView_CreateTournament);
        CardView cardView2 = (CardView)  findViewById(R.id.CardView_SelectStageStriking);
        CardView cardView3 = (CardView) findViewById(R.id.CardView_Notes);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.CardView_CreateTournament) {
//                    Intent startNext = new Intent(v.getContext(), ChooseBetweenTournamentFeaturesActivity.class);
//                    startActivity(startNext);
                }
                else if(v.getId() == R.id.CardView_SelectCharacterData){
                    Intent startNext = new Intent(v.getContext(), CharacterDataSelectionActivity.class);
                    startActivity(startNext);
                }
                else if(v.getId() == R.id.CardView_SelectStageStriking){
                    Intent startNext = new Intent(v.getContext(), ChooseStageListActivity.class);
                    startActivity(startNext);
                }
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
    @Override
    public void onBackPressed(){

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        return true;
    }
}
