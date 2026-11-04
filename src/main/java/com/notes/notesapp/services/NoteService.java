package com.notes.notesapp.services;

import com.notes.notesapp.models.NoteModel;
import com.notes.notesapp.models.ReceiveNoteModel;
import com.notes.notesapp.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteModel> getNotesByUserId(Long user_id) {
        return noteRepository.getNotes(user_id);
    }

    public void createNote(ReceiveNoteModel note) {
        noteRepository.createNote(note);
    }
}
