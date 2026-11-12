package com.notes.notesapp.services;

import com.notes.notesapp.models.InternalReceiveNoteModel;
import com.notes.notesapp.models.NoteModel;
import com.notes.notesapp.models.ReceiveNoteModel;
import com.notes.notesapp.models.ReceiveNotePutMethodModel;
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

    public void createNote(InternalReceiveNoteModel note) {
        noteRepository.createNote(note);
    }

    public ReceiveNoteModel getNoteById(Long user_id, long id) {

        NoteModel allNote = noteRepository.getNoteById(id);
        boolean run = user_id.equals(allNote.user_id);

        if (!run) {
            throw new RuntimeException("User does not have authorization to get the note");
        } else {
            ReceiveNoteModel receiveNoteModel = new ReceiveNoteModel();
            receiveNoteModel.setNoteProperties(allNote);
            return receiveNoteModel;
        }
    }

    public void updateNoteById(ReceiveNotePutMethodModel note, Long real_user_id) {

        try {
            noteRepository.updateNoteById(note, real_user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNoteById(Long id, Long user_id) {
        try {
            noteRepository.deleteNoteById(id, user_id);
        } catch (Exception e) {
            throw new RuntimeException("Or the task do not exists, or the user doesn't have permission to delete the note");
        }
    }
}
