package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.honorsmobileapps.noahshields.smashutilities.R;

import java.util.ArrayList;
import java.util.Map;

public class ChooseStageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_stage_list);
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
        SharedPreferences prefs = this.getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities",MODE_PRIVATE);
        Map<String, ?> allPrefs = prefs.getAll();
        Log.d("Prefs",allPrefs.toString());
        ArrayList<StageListInfo> stageListInfos = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            Log.d("Prefs",entry.getKey());
            if (entry.getKey().contains("com.honorsmobileapps.noahshields.smashutilities.savedstagelist")){
                stageListInfos.add(new StageListInfo(entry.getKey().substring(62),entry.getKey()));
            }
        }
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_StageListSelection);
        StageListAdapter adapter = new StageListAdapter(stageListInfos);
        recyclerView.setAdapter(adapter);
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
