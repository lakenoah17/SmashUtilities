package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.honorsmobileapps.noahshields.smashutilities.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//This activity allows the user to create a stage list and save it to
//shared preferences
public class CreateStageListActivity extends AppCompatActivity {
    private boolean[] selectedStages;
    private static final String SELECTED_STAGES_KEY = "selectedStages";
    final ArrayList<BasicStageInfo> stages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stage_list);

        //Arrays that store all of the stage names and image resources
        final int[] stageImageResources = {R.drawable.battlefield_image, R.drawable.big_battlefield_image, R.drawable.final_destination_image,
                                R.drawable.peachs_castle_image, R.drawable.kongo_jungle_picture, R.drawable.hyrule_castle_sixty_four_image,
                                R.drawable.super_happy_tree_image, R.drawable.dreamland_image, R.drawable.kongo_falls_image,
                                R.drawable.temple_image, R.drawable.brinstar_image, R.drawable.yoshis_story_image,
                                R.drawable.fountain_of_dreams_image, R.drawable.corneria_image, R.drawable.venom_image,
                                R.drawable.pokemon_stadium_one_image, R.drawable.delfino_plaza_image, R.drawable.wario_ware_image,
                                R.drawable.frigate_orpheon_image, R.drawable.yoshis_island_brawl_image, R.drawable.halberd_image,
                                R.drawable.lylat_image, R.drawable.pokemon_stadium_two_image, R.drawable.castle_siege_image,
                                R.drawable.smashville_image, R.drawable.unova_image, R.drawable.picto_chat_image,
                                R.drawable.kalos_image, R.drawable.town_and_city_image, R.drawable.duck_hunt_image};
        String[] stageNameAndSize = {"Battle Field", "Big Battle Field", "Final Destination",
                                    "Peach's Castle", "Kongo Jungle", "Hyrule Castle",
                                    "Super Happy Tree", "Dreamland", "Kongo Falls",
                                    "Temple", "Brinstar", "Yoshi's Story",
                                    "Fountain of\nDreams", "Corneria", "Venom",
                                    "Pokemon Stadium One", "Delfino Plaza", "Wario Ware",
                                    "Frigate Orpheon", "Yoshi's Island (Brawl)", "Halberd",
                                    "Lylat Cruise", "Pokemon Stadium Two", "Castle Siege",
                                    "Smashville", "Unova Pokemon League", "Picto Chat 2",
                                    "Kalos Pokemon League", "Town and City", "Duck Hunt"};


        //When screen rotates the activity is reset. This loads the selected stages from
        //the savedInstanceState bundle if there is a saved state to load from
        selectedStages = new boolean[stageNameAndSize.length];
        if (savedInstanceState != null){
            selectedStages = savedInstanceState.getBooleanArray(SELECTED_STAGES_KEY);
        }

        //Makes all necessary stage info objects
        for (int i = 0; i < stageNameAndSize.length; i++){
            stages.add(new BasicStageInfo(stageImageResources[i], stageNameAndSize[i], selectedStages[i]));
        }

        //Setting up RecyclerView
        StageItemAdapter adapter = new StageItemAdapter(stages);
        RecyclerView stagesRecycler = findViewById(R.id.RecyclerView_StageListCreationSelection);

        //This is to adapt RecyclerView column count based around screen size
        final Display display = getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        final float dpWidth  = outMetrics.widthPixels / density;
        final float dpHeight = outMetrics.widthPixels / density;
        GridLayoutManager gridLayoutManager = (GridLayoutManager)stagesRecycler.getLayoutManager();
        gridLayoutManager.setSpanCount(((int)dpWidth)/200);

        stagesRecycler.setAdapter(adapter);

        //Setting up save stage list button
        Button saveStageList = findViewById(R.id.Button_SaveStageList);
        saveStageList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Makes popup for the save stage list
                View customView = getLayoutInflater().inflate(R.layout.confirm_stage_list_creation_popup, null);
                final PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setElevation(20);
                popupWindow.setEnterTransition(new Explode());

                //Allows for window to be focused
                popupWindow.setFocusable(true);

                //Retrieving all of the components of the popup for access
                ImageButton closePopup = customView.findViewById(R.id.ImageButton_Close);
                Button submit = customView.findViewById(R.id.Button_CreateStageList);

                final EditText submitName = customView.findViewById(R.id.EditText_NameStageList);
                submitName.setFocusable(true);
                popupWindow.update();

                popupWindow.showAtLocation((ViewGroup)v.getParent(), Gravity.CENTER,0,0);


                //For when the user submits the stage list they created
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Makes HashSet for easy storage in memory
                        Set<String> set = new HashSet<String>();
                        for(int i = 0; i < stages.size(); i++){
                            if (stages.get(i).getSelected()){
                                set.add(stages.get(i).getStageName());
                            }
                        }

                        //Saving the stage list to Shared Preferences
                        SharedPreferences sharedPreferences = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String stageListName = "com.honorsmobileapps.noahshields.smashutilities.savedstagelist" + submitName.getText().toString();
                        editor.putStringSet(stageListName, set);
                        editor.apply();

                       //Intent returnToStageSelections = new Intent(v.getContext(), ChooseStageListActivity.class);
                       //startActivity(returnToStageSelections);

                        finish();
                    }
                });
                closePopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        for (int i = 0; i < stages.size(); i++){
            selectedStages[i] = stages.get(i).getSelected();
        }
        savedInstanceState.putBooleanArray(SELECTED_STAGES_KEY, selectedStages);
        //declare values before saving the state
        super.onSaveInstanceState(savedInstanceState);
    }
}
