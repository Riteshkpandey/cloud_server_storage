package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class StorageController {

    private StorageService storageService;
    private UserService userService;

    public StorageController(StorageService storageService, UserService userService){
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping("/download-file/{fileName:.+}")
    public ResponseEntity downloadFile(@PathVariable String fileName, Authentication authentication){

        int currentUserId = userService.getUserId(authentication.getName());
        File file = storageService.getFile(fileName, currentUserId);

        if(file != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+fileName+"\"")
                    .body(file.getFileData());
        }
        return null;
    }

    @GetMapping("/delete-file/{fileId:.+}")
    public String deleteFile(@PathVariable int fileId, Model model){
        String errorMessage = null;

        int deletedRows = storageService.deleteFile(fileId);

        if(deletedRows < 1){
            errorMessage = "Error! Can't delete the file";
        }

        if(errorMessage == null){
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication){
        String errorMessage = null;

        int currentUserId = userService.getUserId(authentication.getName());

        if(!storageService.isUniqueFileName(file.getOriginalFilename(), currentUserId)){
            errorMessage = "Error! File name not unique";
        }

        if(file.isEmpty()){
            errorMessage = "Error! No file selected";
        }

        if(errorMessage == null){
            try {
                int fileId = storageService.store(file, currentUserId);
                if(fileId < 0){
                    errorMessage = "There was an error uploading your file.";
                }
            } catch (IOException ioException){
                errorMessage = "There was an error uploading your file.";
            }
        }

        if(errorMessage == null){
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadFail", errorMessage);
        }

        return "result";
    }


}
