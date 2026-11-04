package com.notes.notesapp.models;

import java.time.LocalDate;

public class NoteModel {

    public Long id;
    public Long user_id;
    public String title;
    public String content;
    public LocalDate created_at;
    public LocalDate updated_at;
}
