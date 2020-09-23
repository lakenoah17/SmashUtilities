package com.honorsmobileapps.noahshields.smashutilities.Notes;

//Data class that stores the information of a note
//stored in Shared Preferences
public class NoteData {

    private String noteName;
    private String noteKey;
    private String noteType;

    //Constructs the NoteData
    //Params:
    //  noteName - the name of the note
    //  noteKey - the key of the note in Shared Preferences
    //  noteType - the format of the note
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
