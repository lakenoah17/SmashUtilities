package com.honorsmobileapps.noahshields.smashutilities.Notes;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;

import java.util.ArrayList;

//Works as the adapter for the RecyclerView allowing the user to pick notes
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    //List of all of the notes in the list
    private ArrayList<NoteData> notes;

    //ViewHolder for the RecyclerView
    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private NoteData note;

        //Constructs the NoteViewHolder
        //Params:
        //  itemView - the item in the RecyclerView
        public NoteViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.TextView_StageListName);
            itemView.setOnClickListener(this);
        }

        //Sets all required data in the itemView
        //Params:
        //  note - the data needed to populate the itemView
        public void bindNoteData(NoteData note){
            this.note = note;
            name.setText(note.getNoteName());
        }

        //When the user clicks an item it starts the NoteActivity
        //with the necessary data to show the selected note
        //Params:
        //  v - the itemView that was clicked
        @Override
        public void onClick(View v) {
            //Sets up the intent to use
            Intent startNext = new Intent(v.getContext(), NoteActivity.class);
            startNext.putExtra("SharedPrefKey",note.getNoteKey());
            startNext.putExtra("NoteName",note.getNoteName());
            startNext.putExtra("NoteFormat",note.getNoteType());

            //Starts the activity NoteActivity
            v.getContext().startActivity(startNext);
        }
    }

    //Constructs the adapter
    //Params:
    //  notes - the list of all of the notes retrieved from
    //          Shared Preferences
    public NoteAdapter(ArrayList<NoteData> notes){
        this.notes = notes;
    }

    //Returns the size of the RecyclerView
    @Override
    public int getItemCount() {
        return notes.size();
    }

    //Sets up the ViewHolders for the RecyclerView
    //Params:
    //  viewGroup - the ViewGroup to populate
    //  i - the index of the particular view int the RecyclerView
    //Returns: The ViewHolder for the specified itemView
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.stage_list_item, viewGroup, false);
        return new NoteAdapter.NoteViewHolder(view);
    }

    //Binds the data to the ViewHolder
    //Params:
    //  holder - the ViewHolder to bind data to
    //  position - the index of the note to bind to the ViewHolder
    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        NoteData note = notes.get(position);
        holder.bindNoteData(note);
    }

    //Sets the notes to use for the RecyclerView
    //Params:
    //  notes - list of all of the notes retrieved from Shared Preferences
    public void setNotes(ArrayList<NoteData> notes){
        this.notes = notes;
    }
}
