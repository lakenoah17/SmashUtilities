package com.honorsmobileapps.noahshields.smashutilities.Notes;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;
import com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature.StageListInfo;
import com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature.StageStrikingActivity;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<NoteData> notes;
    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private NoteData note;

        public NoteViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.TextView_StageListName);
            itemView.setOnClickListener(this);
        }
        public void bindNoteData(NoteData note){
            this.note = note;
            name.setText(note.getNoteName());
        }
        @Override
        public void onClick(View v) {
            Intent startNext = new Intent(v.getContext(), NoteActivity.class);
            startNext.putExtra("SharedPrefKey",note.getNoteKey());
            startNext.putExtra("NoteName",note.getNoteName());
            startNext.putExtra("NoteFormat",note.getNoteType());
            v.getContext().startActivity(startNext);
        }
    }
    public NoteAdapter(ArrayList<NoteData> notes){
        this.notes = notes;
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.stage_list_item, viewGroup, false);
        return new NoteAdapter.NoteViewHolder(view);
    }
    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        NoteData note = notes.get(position);
        holder.bindNoteData(note);
    }
    public void setNotes(ArrayList<NoteData> notes){
        this.notes = notes;
    }
}
