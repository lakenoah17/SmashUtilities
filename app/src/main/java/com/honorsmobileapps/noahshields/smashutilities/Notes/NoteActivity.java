package com.honorsmobileapps.noahshields.smashutilities.Notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;

//This activity shows the note retrieved from SharedPreferences and
//allows the user to edit it
public class NoteActivity extends AppCompatActivity {
    //Key of the SharedPreference
    private String key;
    //The format of the note. Currently either "blank" or "combo"
    private String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gets the intent that started this activity
        Intent intent = this.getIntent();

        //Gets the info necessary to the note
        key = intent.getStringExtra("SharedPrefKey");
        String name = intent.getStringExtra("NoteName");
        format = intent.getStringExtra("NoteFormat");

        //Allows for user to view a combo formatted note
        if (format.equals("combo")) {
            setContentView(R.layout.combo_note_format);
            //Array with all of the characters names stored
            String[] characterNames = {"Bayonetta", "Bowser", "Bowser Jr.", "Captain Falcon", "Cloud", "Corrin",
                    "Daisy", "Dark Pit", "Diddy Kong", "Donkey Kong", "Dr. Mario", "Duck Hunt", "Falco", "Fox", "Ganondorf",
                    "Greninja", "Ice Climbers", "Inkling", "Ike", "Jigglypuff", "King Dedede", "Kirby", "Link", "Little Mac",
                    "Lucario", "Lucas", "Lucina", "Luigi", "Mario", "Marth", "Mega Man", "Meta Knight", "Mewtwo",
                    "Mii Fighter", "Mr. Game & Watch", "Ness", "Olimar", "Pac-Man", "Palutena", "Peach", "Pichu", "Pikachu",
                    "Pit", "Pokémon Trainer", "Ridley", "R.O.B.", "Robin", "Rosalina", "Roy", "Ryu", "Samus", "Sheik", "Shulk",
                    "Snake", "Sonic", "Toon Link", "Villager", "Wario", "Wii Fit Trainer", "Wolf", "Yoshi", "Young Link", "Zelda",
                    "Zero Suit Samus", "Simon Belmont", "Richter", "Ken", "Incineroar", "Piranha Plant", "Dark Samus", "Chrom",
                    "Simon", "Richter", "King K. Rool", "Isabelle"};

            //Layout all of the combo data is stored in
            GridLayout comboGrid = (GridLayout) findViewById(R.id.GridLayout_comboGrid);
            String unformattedNote = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE).getString(key,"");

            //Sets all of the params of the layout
            GridLayout.LayoutParams param;

            //Sets up all of the rows and columns to use in the combo note
            int row = 1;
            for (String characterName : characterNames){
                //Creates new set of params to use for dynamic creation
                param = new GridLayout.LayoutParams();
                param.leftMargin = 2;
                param.rightMargin = 2;
                param.topMargin = 2;
                param.bottomMargin = 2;
                param.setGravity(Gravity.FILL);
                param.columnSpec = GridLayout.spec(0,1,1f);
                param.rowSpec = GridLayout.spec(row,1,1f);

                //Sets the text and adds the new TextView to the layout
                TextView data = (TextView) getLayoutInflater().inflate(R.layout.text_view_grid_cell_template, null);
                data.setText(characterName);
                comboGrid.addView(data,param);

                for(int col = 1; col < 3; col++){
                    EditText percent = (EditText) getLayoutInflater().inflate(R.layout.combo_percent_layout, null);

                    //Creates new set of params to use for dynamic creation
                    param = new GridLayout.LayoutParams();
                    param.leftMargin = 2;
                    param.rightMargin = 2;
                    param.topMargin = 2;
                    param.bottomMargin = 2;
                    param.setGravity(Gravity.FILL | Gravity.CENTER);
                    param.columnSpec = GridLayout.spec(col,1,1f);
                    param.rowSpec = GridLayout.spec(row,1,1f);

                    //Interprets saved data into the combo note
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
        //Loads a un-formatted note into the activity
        else {
            setContentView(R.layout.activity_note);
            EditText text = findViewById(R.id.EditText_Note);
            text.setText(getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE).getString(key,""));
        }

        //Sets the text at the top of the screen
        ((TextView)findViewById(R.id.TextView_NoteName)).setText(name);
    }
    @Override
    protected void onStop(){
        super.onStop();

        //Saves the combo note the shared preferences when the activity stops.
        if (format.equals("combo")){

            //Sets the seperator to use between data
            String separator = "+*+";
            String dataString = "";
            GridLayout comboGrid = (GridLayout) findViewById(R.id.GridLayout_comboGrid);

            //Saves all of to a single string to store in shared preferences
            for(int i = 3; i < comboGrid.getChildCount(); i++){

                //Doesn't write the characters name to memory
                if (i%3!=0){
                    //Fixes the issue where if the user inputs "+*+" the program breaks
                    if (!((EditText)comboGrid.getChildAt(i)).getText().toString().contains("+*+"))
                        dataString+=((EditText)comboGrid.getChildAt(i)).getText().toString()+separator;
                    else
                        dataString+=separator;
                }
            }

            //Gets the SharedPreferences related to the app
            SharedPreferences sharedPreferences = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(key, dataString);
            editor.apply();
        }

        //Saves the blank formatted note to shared preferences
        if (format.equals("blank")){
            //Gets the SharedPreferences related to the app
            SharedPreferences sharedPreferences = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //Gets and puts the string into SharedPreferences
            EditText blankNoteText = findViewById(R.id.EditText_Note);
            editor.putString(key, blankNoteText.getText().toString());
            editor.apply();
        }
    }
}
