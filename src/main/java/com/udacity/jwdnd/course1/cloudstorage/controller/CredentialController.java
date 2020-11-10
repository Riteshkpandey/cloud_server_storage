package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {

    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping("/delete-credential/{credId:.+}")
    public String deleteCredential(@PathVariable int credId, Model model){
        String errorMessage = null;

        int deletedRows = credentialService.deleteCredential(credId);

        if(deletedRows < 1){
            errorMessage = "Error! Can't delete the credential";
        }

        if(errorMessage == null){
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }

    @GetMapping("/get-decrypted-password/{credId:.+}")
    public ResponseEntity<String> getDecryptedPassword(@PathVariable(value = "credId") String credentialId){
        Credential credential = credentialService.getCredential(Integer.parseInt(credentialId));
        String decPass = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        return ResponseEntity.ok(decPass);
    }


    @PostMapping("/add-credential")
    public String addCredential(@ModelAttribute("new-credential") Credential credential, Model model, Authentication authentication){
        String errorMessage = null;
        boolean toUpdate = false;
        int currentUserId = userService.getUserId(authentication.getName());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        credential.setUserId(currentUserId);
        credential.setKey(encodedKey);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));

        if(credential.getCredentialId() != null){
            int credsUpdated = credentialService.updateCredential(credential);
            if(credsUpdated < 1){
                errorMessage = "There was an error updating your credential.";
            }
            toUpdate = true;
        } else {
            int credRows = credentialService.addCredential(credential);

            if (credRows < 1) {
                errorMessage = "There was an error uploading your Credentials";
            }
        }

        if(errorMessage == null){
            if(toUpdate){
                model.addAttribute("updateSuccess", true);
            } else {
                model.addAttribute("uploadSuccess", true);
            }
        } else {
            model.addAttribute("uploadError", errorMessage);
        }

        return "result";
    }
}
