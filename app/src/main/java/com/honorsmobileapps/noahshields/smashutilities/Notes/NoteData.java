package com.honorsmobileapps.noahshields.smashutilities.Notes;

public class NoteData {

    private String noteName;
    private String noteKey;
    private String noteType;

    public NoteData(String noteName, String noteKey, String noteType){
        this.noteName = noteName;
        this.noteKey = noteKey;
        this.noteType = noteType;
    }

    public String getNoteKey() {
        return noteKey;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteType() {
        return noteType;
    }
}
