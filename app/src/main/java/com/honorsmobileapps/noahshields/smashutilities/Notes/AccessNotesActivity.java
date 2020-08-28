package com.honorsmobileapps.noahshields.smashutilities.Notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.honorsmobileapps.noahshields.smashutilities.R;
import com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature.CreateStageListActivity;
import com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature.StageListAdapter;
import com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature.StageListInfo;

import java.util.ArrayList;
import java.util.Map;

public class AccessNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_notes);

        SharedPreferences prefs = this.getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities",MODE_PRIVATE);
        Map<String, ?> allPrefs = prefs.getAll();
        ArrayList<NoteData> notes = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            Log.d("Prefs",entry.getKey());
            if (entry.getKey().contains("com.honorsmobileapps.noahshields.smashutilities.note")){
                notes.add(new NoteData(entry.getKey().substring(57),entry.getKey(),entry.getKey().substring(52,57)));
            }
        }
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_Notes);
        NoteAdapter adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);
        Button createNote = findViewById(R.id.Button_CreateNote);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateNoteActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
