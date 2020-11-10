package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(UserService userService, NoteService noteService){
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/delete-note/{noteId:.+}")
    public String deleteNote(@PathVariable int noteId, Model model){
        String errorMessage = null;

        int deletedRows = noteService.deleteNote(noteId);

        if(deletedRows < 1){
            errorMessage = "Error! Can't delete the note";
        }

        if(errorMessage == null){
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }

    @PostMapping("/upload-note")
    public String uploadNote(@ModelAttribute("newNote") Note note, Model model, Authentication authentication){
        String errorMessage = null;
        boolean toUpdate = false;

        if(note.getNoteId() != null){
            // Update Note
            int notesUpdated = noteService.updateNote(note);
            if(notesUpdated < 1){
                errorMessage = "There was an error updating your note.";
            }
            toUpdate = true;
        }

        if(!toUpdate) {
            int currentUserId = userService.getUserId(authentication.getName());

            if (!noteService.isUniqueNoteName(note.getNoteTitle(), currentUserId)) {
                errorMessage = "Error! Note name not unique";
            }

            if (errorMessage == null) {
                note.setUserId(currentUserId);
                int noteId = noteService.addNote(note);
                if (noteId < 0) {
                    errorMessage = "There was an error uploading your note.";
                }
            }
        }

        if(errorMessage == null){
            if(toUpdate){
                model.addAttribute("updateSuccess", true);
            } else {
                model.addAttribute("uploadSuccess", true);
            }
        } else {
            model.addAttribute("uploadFail", errorMessage);
        }

        return "result";
    }
}
