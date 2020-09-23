package com.honorsmobileapps.noahshields.smashutilities.Notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.honorsmobileapps.noahshields.smashutilities.R;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Button submitButton = findViewById(R.id.Button_SubmitNote);
        final RadioButton comboFormatButton = findViewById(R.id.RadioButton_ComboFormat);
        final EditText name = findViewById(R.id.EditText_NameNote);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("com.honorsmobileapps.noahshields.smashutilities", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String temp = "blank";
                if (comboFormatButton.isChecked()){
                    temp="combo";
                }
                String noteName = "com.honorsmobileapps.noahshields.smashutilities.note"+temp+"$"+name.getText().toString();
                editor.putString(noteName, "");
                editor.commit();
                Intent returnToStageSelections = new Intent(v.getContext(), AccessNotesActivity.class);
                startActivity(returnToStageSelections);
            }
        });
    }
}
