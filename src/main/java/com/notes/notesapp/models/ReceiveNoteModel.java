package com.notes.notesapp.models;

public class ReceiveNoteModel {
    public String title;
    public String content;

    public void setNoteProperties(NoteModel note) {
        this.title = note.title;
        this.content = note.content;
    }
}
