package com.honorsmobileapps.noahshields.smashutilities.Notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.honorsmobileapps.noahshields.smashutilities.R;

import java.util.ArrayList;
import java.util.Map;

//This activity shows the user a list of their previously created notes
//as well as a but to create a new note
public class AccessNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_notes);

        //Retrieves the shared preferences related to this app and stores them in a map
        SharedPreferences prefs = this.getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities",
                                                            MODE_PRIVATE);
        Map<String, ?> allPrefs = prefs.getAll();

        //Creates list of the notes retrieved
        ArrayList<NoteData> notes = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            //Makes sure the data used is a note
            if (entry.getKey().contains("com.honorsmobileapps.noahshields.smashutilities.note")){
                String key = entry.getKey();
                notes.add(new NoteData(key.substring(key.indexOf('$') + 1),
                                        key,
                                        key.substring(52, key.indexOf('$'))));
            }
        }

        //Sets up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_Notes);
        NoteAdapter adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);

        //Sets up the Button to create notes
        Button createNote = findViewById(R.id.Button_CreateNote);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Starts CreateNoteActivity
                Intent intent = new Intent(v.getContext(), CreateNoteActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
