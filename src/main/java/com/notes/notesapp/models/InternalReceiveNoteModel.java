package com.notes.notesapp.models;

public class InternalReceiveNoteModel {

    public Long user_id;
    public String title;
    public String content;

    public InternalReceiveNoteModel(Long user_id,ReceiveNoteModel note) {
        this.user_id = user_id;
        this.title = note.title;
        this.content = note.content;
    }
}
