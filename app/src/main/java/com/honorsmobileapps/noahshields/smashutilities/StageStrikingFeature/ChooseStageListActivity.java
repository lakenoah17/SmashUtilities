package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.honorsmobileapps.noahshields.smashutilities.R;

import java.util.ArrayList;
import java.util.Map;

//This activity allows the user to either create a
//stage list or access one they already stored in
//shared preferences
public class ChooseStageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_stage_list);

        //Access the shared preferences
        SharedPreferences prefs = this.getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities",MODE_PRIVATE);
        Map<String, ?> allPrefs = prefs.getAll();

        ArrayList<StageListInfo> stageListInfos = new ArrayList<>();

        //Loops through all of the entries in the set
        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            //Gets all of the preferences that are saved as a savedstagelist
            if (entry.getKey().contains("com.honorsmobileapps.noahshields.smashutilities.savedstagelist")){
                //Parses data into a StageListInfo object
                stageListInfos.add(new StageListInfo(entry.getKey().substring(62), entry.getKey()));
            }
        }

        //Sets up the RecyclerView for the activity
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_StageListSelection);
        StageListAdapter adapter = new StageListAdapter(stageListInfos);
        recyclerView.setAdapter(adapter);

        //Sets up the button to create a new stage list
        Button button = findViewById(R.id.Button_CreateStageListChoose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateStageListActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
