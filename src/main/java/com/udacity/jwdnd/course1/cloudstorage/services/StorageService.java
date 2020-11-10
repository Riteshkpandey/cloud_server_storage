package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StorageService {

    private FilesMapper filesMapper;

    public StorageService(FilesMapper mapper){
        this.filesMapper = mapper;
    }

    public boolean isUniqueFileName(String fileName, int userId){
        return filesMapper.getFile(fileName, userId) == null;
    }

    public int store(MultipartFile multipartFile, int userId) throws IOException {
        File file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                multipartFile.getSize(), userId, multipartFile.getBytes());
        return filesMapper.storeFile(file);
    }

    public List<File> getFilesForUser(int userId){
        return filesMapper.getFiles(userId);
    }

    public File getFile(String fileName, int userId){
        return filesMapper.getFile(fileName, userId);
    }

    public int deleteFile(int fileId){
        return filesMapper.deleteFileById(fileId);
    }

}
