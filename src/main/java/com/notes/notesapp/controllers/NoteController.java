package com.notes.notesapp.controllers;

import com.notes.notesapp.models.NoteModel;
import com.notes.notesapp.models.ReceiveNoteModel;
import com.notes.notesapp.services.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    private final NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes/get-all-by-user-id/{user_id}")
    public List<NoteModel> getNotes(@PathVariable Long user_id) {
        return noteService.getNotesByUserId(user_id);
    }

    @PostMapping("/notes/create")
    public void createNote(@RequestBody ReceiveNoteModel note) {
        noteService.createNote(note);
    }
}
