package com.honorsmobileapps.noahshields.smashutilities.Notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;

public class NoteActivity extends AppCompatActivity {
    private String key;
    private String format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        key = intent.getStringExtra("SharedPrefKey");
        String name = intent.getStringExtra("NoteName");
        format = intent.getStringExtra("NoteFormat");
        if (format.equals("combo")) {
            setContentView(R.layout.combo_note_format);
            String[] characterNames = {"Bayonetta", "Bowser", "Bowser Jr.", "Captain Falcon", "Cloud", "Corrin",
                    "Daisy", "Dark Pit", "Diddy Kong", "Donkey Kong", "Dr. Mario", "Duck Hunt", "Falco", "Fox", "Ganondorf",
                    "Greninja", "Ice Climbers", "Inkling", "Ike", "Jigglypuff", "King Dedede", "Kirby", "Link", "Little Mac",
                    "Lucario", "Lucas", "Lucina", "Luigi", "Mario", "Marth", "Mega Man", "Meta Knight", "Mewtwo",
                    "Mii Fighter", "Mr. Game & Watch", "Ness", "Olimar", "Pac-Man", "Palutena", "Peach", "Pichu", "Pikachu",
                    "Pit", "Pokémon Trainer", "Ridley", "R.O.B.", "Robin", "Rosalina", "Roy", "Ryu", "Samus", "Sheik", "Shulk",
                    "Snake", "Sonic", "Toon Link", "Villager", "Wario", "Wii Fit Trainer", "Wolf", "Yoshi", "Young Link", "Zelda",
                    "Zero Suit Samus", "Simon Belmont", "Richter", "Ken", "Incineroar", "Piranha Plant", "Dark Samus", "Chrom",
                    "Simon", "Richter", "King K. Rool", "Isabelle"};

            GridLayout comboGrid = (GridLayout) findViewById(R.id.GridLayout_comboGrid);
            String unformattedNote = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE).getString(key,"");
            int row = 1;
            for (String characterName : characterNames){
                TextView data = (TextView) getLayoutInflater().inflate(R.layout.text_view_grid_cell_template, null);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.columnSpec = GridLayout.spec(0,1,1f);
                param.rowSpec = GridLayout.spec(row,1,1f);
                param.leftMargin = 2;
                param.rightMargin = 2;
                param.topMargin = 2;
                param.bottomMargin = 2;
                param.setGravity(Gravity.FILL);

                data.setText(characterName);
                comboGrid.addView(data,param);

                for(int i = 1; i < 3; i++){
                    EditText percent = (EditText) getLayoutInflater().inflate(R.layout.combo_percent_layout, null);
                    param = new GridLayout.LayoutParams();
                    param.columnSpec = GridLayout.spec(i,1,1f);
                    param.rowSpec = GridLayout.spec(row,1,1f);
                    param.leftMargin = 2;
                    param.rightMargin = 2;
                    param.topMargin = 2;
                    param.bottomMargin = 2;
                    param.setGravity(Gravity.FILL);
                    if (!unformattedNote.equals("")) {
                        percent.setText(unformattedNote.substring(0, unformattedNote.indexOf("+*+")));
                        unformattedNote = unformattedNote.substring(unformattedNote.indexOf("+*+") + 3);
                    }
                    comboGrid.addView(percent,param);
                }
                row++;
                comboGrid.setRowCount(row+1);
            }
        }
        else {
            setContentView(R.layout.activity_note);
            EditText text = findViewById(R.id.EditText_Note);
            text.setText(getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE).getString(key,""));
        }
        ((TextView)findViewById(R.id.TextView_NoteName)).setText(name);
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (format.equals("combo")){
            String separator = "+*+";
            String temp = "";
            GridLayout comboGrid = (GridLayout) findViewById(R.id.GridLayout_comboGrid);

            for(int i = 3; i < comboGrid.getChildCount(); i++){
                if (i%3!=0){
                    if (!((EditText)comboGrid.getChildAt(i)).getText().toString().equals("+*+"))
                        temp+=((EditText)comboGrid.getChildAt(i)).getText().toString()+separator;
                    else
                        temp+=separator;
                }
            }
            SharedPreferences sharedPreferences = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(key, temp);
            editor.commit();
        }
        if (format.equals("blank")){
            SharedPreferences sharedPreferences = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            EditText blankNoteText = findViewById(R.id.EditText_Note);
            editor.putString(key, blankNoteText.getText().toString());
            editor.commit();
        }
    }
}
