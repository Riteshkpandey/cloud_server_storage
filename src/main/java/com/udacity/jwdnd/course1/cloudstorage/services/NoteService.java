package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper){
        this.notesMapper = notesMapper;
    }

    public boolean isUniqueNoteName(String noteTitle, int userId){
        return notesMapper.getNote(noteTitle, userId) == null;
    }

    public boolean noteExists(int noteId){
        return notesMapper.getNoteById(noteId) != null;
    }

    public int addNote(Note note){
        return notesMapper.addNote(note);
    }

    public List<Note> getNotes(int userId){
        return notesMapper.getNotesByUser(userId);
    }

    public Note getNote(String noteTitle, int userId){
        return notesMapper.getNote(noteTitle, userId);
    }

    public Note getNote(int noteId){
        return notesMapper.getNoteById(noteId);
    }

    public int updateNote(Note note){
        return notesMapper.updateNote(note);
    }

    public int deleteNote(int noteId){
        return notesMapper.deleteNoteById(noteId);
    }

}
