package com.notes.notesapp.controllers;

import com.notes.notesapp.models.InternalReceiveNoteModel;
import com.notes.notesapp.models.NoteModel;
import com.notes.notesapp.models.ReceiveNoteModel;
import com.notes.notesapp.models.ReceiveNotePutMethodModel;
import com.notes.notesapp.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    private final NoteService noteService;
    private final UserController userController;
    public NoteController(NoteService noteService, UserController userController) {
        this.noteService = noteService;
        this.userController = userController;
    }

    @GetMapping("/notes/get-all-by-current-user")
    public List<NoteModel> getNotes(Authentication authentication) {
        String email = authentication.getName();
        Long id = userController.getUserId(email);
        return noteService.getNotesByUserId(id);
    }

    @PostMapping("/notes/create")
    public void createNote(@RequestBody ReceiveNoteModel receivingNote, Authentication authentication) {
        String email = authentication.getName();
        Long id = userController.getUserId(email);
        InternalReceiveNoteModel note = new InternalReceiveNoteModel(id, receivingNote);
        noteService.createNote(note);
    }

    @GetMapping("/notes/get-note-by-id/{id}")
    public ReceiveNoteModel getNoteById(Authentication authentication, @PathVariable Long id) {
        String email = authentication.getName();
        Long user_id = userController.getUserId(email);

        return noteService.getNoteById(user_id, id);
    }

    @PatchMapping("/notes/update-note-by-id")
    public void updateNoteById(@RequestBody ReceiveNotePutMethodModel note, Authentication authentication) {
        String email = authentication.getName();
        Long user_id = userController.getUserId(email);

        noteService.updateNoteById(note, user_id);
    }

    @DeleteMapping("/notes/delete-note-by-id/{id}")
    public void deleteNoteById(Authentication authentication, @PathVariable Long id) {
        String email = authentication.getName();
        Long user_id = userController.getUserId(email);

        noteService.deleteNoteById(user_id, id);
    }
}
