package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getCredentials(int userId){
        return credentialMapper.getCredentialsByUser(userId);
    }

    public Credential getCredential(int credentialId){
        return credentialMapper.getCredentialById(credentialId);
    }

    public int addCredential(Credential credential){
        return credentialMapper.addCredential(credential);
    }

    public int updateCredential(Credential credential){
        return credentialMapper.updateCredentials(credential);
    }

    public int deleteCredential(int credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }
}
