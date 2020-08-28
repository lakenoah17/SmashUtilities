package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class StageStrikingActivity extends AppCompatActivity {
    private boolean[] strikedStages;
    private static final String STRIKED_STAGES_KEY = "strikedStages";
    private ArrayList<BasicStageInfo> stages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_striking);

        int[] stageImageResources = {R.drawable.battlefield_image, R.drawable.big_battlefield_image, R.drawable.final_destination_image,
                R.drawable.peachs_castle_image, R.drawable.kongo_jungle_picture, R.drawable.hyrule_castle_sixty_four_image,
                R.drawable.super_happy_tree_image, R.drawable.dreamland_image, R.drawable.kongo_falls_image,
                R.drawable.temple_image, R.drawable.brinstar_image, R.drawable.yoshis_story_image,
                R.drawable.fountain_of_dreams_image, R.drawable.corneria_image, R.drawable.venom_image,
                R.drawable.pokemon_stadium_one_image, R.drawable.delfino_plaza_image, R.drawable.wario_ware_image,
                R.drawable.frigate_orpheon_image, R.drawable.yoshis_island_brawl_image, R.drawable.halberd_image,
                R.drawable.lylat_image, R.drawable.pokemon_stadium_two_image, R.drawable.castle_siege_image,
                R.drawable.smashville_image, R.drawable.unova_image, R.drawable.picto_chat_image,
                R.drawable.kalos_image, R.drawable.town_and_city_image, R.drawable.duck_hunt_image};


        String[] stageNameAndSize = new String[]{"Battle Field", "Big Battle Field", "Final Destination",
                "Peach's Castle", "Kongo Jungle", "Hyrule Castle",
                "Super Happy Tree", "Dreamland", "Kongo Falls",
                "Temple", "Brinstar", "Yoshi's Story",
                "Fountain of\nDreams", "Corneria", "Venom",
                "Pokemon Stadium One", "Delfino Plaza", "Wario Ware",
                "Frigate Orpheon", "Yoshi's Island (Brawl)", "Halberd",
                "Lylat Cruise", "Pokemon Stadium Two", "Castle Siege",
                "Smashville", "Unova Pokemon League", "Picto Chat 2",
                "Kalos Pokemon League", "Town and City", "Duck Hunt"};

        stages = new ArrayList<>();
        String key = getIntent().getStringExtra("SharedPrefKey");
        String stageListName = getIntent().getStringExtra("StageListName");


        Set<String> defSet = new Set<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };

        Set<String> stagesSet = this.getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE).getStringSet(key, defSet);

        boolean openRun = true;

        if (savedInstanceState != null){
            strikedStages = savedInstanceState.getBooleanArray(STRIKED_STAGES_KEY);
            openRun = false;
        }


        for (String a : stagesSet){
            for (int i = 0; i < stageNameAndSize.length; i++){
                if (openRun && a.equals(stageNameAndSize[i])){
                    stages.add(new BasicStageInfo(stageImageResources[i], stageNameAndSize[i], false));
                }
                else if (a.equals(stageNameAndSize[i])){
                    stages.add(new BasicStageInfo(stageImageResources[i], stageNameAndSize[i], strikedStages[stages.size()]));
                }
            }
        }

        if (openRun){
            strikedStages = new boolean[stages.size()];
        }

        //Setting up RecyclerView
        StageItemAdapter adapter = new StageItemAdapter(stages);
        RecyclerView stagesRecycler = findViewById(R.id.RecyclerView_StageListStriking);
        TextView title = findViewById(R.id.TextView_StageListNameChosen);
        title.setText(stageListName);

        //This is to adapt RecyclerView column count based around screen size
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        GridLayoutManager gridLayoutManager = (GridLayoutManager)stagesRecycler.getLayoutManager();
        gridLayoutManager.setSpanCount(((int)dpWidth)/232);

        stagesRecycler.setAdapter(adapter);
    }

    //Saves the current state of the striking
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        for (int i = 0; i < stages.size(); i++){
            strikedStages[i] = stages.get(i).getSelected();
        }

        savedInstanceState.putBooleanArray(STRIKED_STAGES_KEY, strikedStages);

        //declare values before saving the state
        super.onSaveInstanceState(savedInstanceState);
    }
}
