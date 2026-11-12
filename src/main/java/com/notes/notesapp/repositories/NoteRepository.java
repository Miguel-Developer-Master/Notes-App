package com.notes.notesapp.repositories;

import com.notes.notesapp.models.InternalReceiveNoteModel;
import com.notes.notesapp.models.NoteModel;
import com.notes.notesapp.models.ReceiveNoteModel;
import com.notes.notesapp.models.ReceiveNotePutMethodModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class NoteRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NoteModel> getNotes(Long user_id) {

        String SQL = "SELECT * FROM NOTES WHERE user_id = ?";
        List<NoteModel> notes = jdbcTemplate.query(SQL, (result, rowNum) -> {
            NoteModel note = new NoteModel();

            note.id = result.getLong("id");
            note.user_id = result.getLong("user_id");
            note.title = result.getString("title");
            note.content = result.getString("content");
            note.created_at = result.getObject("created_at", LocalDate.class);
            note.updated_at = result.getObject("updated_at", LocalDate.class);

            return note;
        }, user_id);

        return notes;
    }

    public void createNote(InternalReceiveNoteModel note) {
        String SQL = "INSERT INTO notes (user_id, title, content) VALUES (?, ?, ?)";

        jdbcTemplate.update(SQL, note.user_id, note.title, note.content);
    }

    public NoteModel getNoteById(Long id) {
        String SQL = "SELECT * FROM NOTES WHERE id = ?";
        List<NoteModel> noteModelNote = jdbcTemplate.query(SQL, (result, rowNum) -> {
            NoteModel note = new NoteModel();

            note.id = result.getLong("id");
            note.user_id = result.getLong("user_id");
            note.title = result.getString("title");
            note.content = result.getString("content");
            note.created_at = result.getObject("created_at", LocalDate.class);
            note.updated_at = result.getObject("updated_at", LocalDate.class);

            return note;
        }, id);

        return noteModelNote.getFirst();
    }

    public void updateNoteById(ReceiveNotePutMethodModel note, Long user_id) throws Exception {
        String SQL = "UPDATE NOTES SET title = ?, content = ?, updated_at = ? WHERE id = ? AND user_id = ?";

        jdbcTemplate.update(SQL, note.title, note.content, note.updated_at, note.id, user_id);
    }
}
