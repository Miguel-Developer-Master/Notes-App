package com.notes.notesapp.repositories;

import com.notes.notesapp.models.NoteModel;
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
            note.created_at = result.getObject("created_at", LocalDate.class);
            note.updated_at = result.getObject("updated_at", LocalDate.class);

            return note;
        }, user_id);

        return notes;
    }
}
