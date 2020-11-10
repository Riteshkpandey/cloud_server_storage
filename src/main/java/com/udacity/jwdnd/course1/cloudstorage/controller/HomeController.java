package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private StorageService storageService;
    private NoteService noteService;
    private CredentialService credentialService;
    private UserService userService;

    private List<File> fileList;
    private List<Note> noteList;
    private List<Credential> credentialList;

    public HomeController(StorageService storageService, NoteService noteService, CredentialService credentialService, UserService userService) {
        this.storageService = storageService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostConstruct
    public void postConstruct(){
        fileList = new ArrayList<>();
        noteList = new ArrayList<>();
        credentialList = new ArrayList<>();
    }


    @GetMapping
    public String getHome(Model model, Authentication authentication){
        int userId = userService.getUserId(authentication.getName());

        fileList = storageService.getFilesForUser(userId);
        noteList = noteService.getNotes(userId);
        credentialList = credentialService.getCredentials(userId);

        model.addAttribute("fileList", fileList);
        model.addAttribute("noteList", noteList);
        model.addAttribute("credentialList", credentialList);

        return "home";
    }
}
