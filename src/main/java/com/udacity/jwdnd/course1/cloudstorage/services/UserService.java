package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private HashService hashService;
    private UserMapper userMapper;

    public UserService(HashService service, UserMapper userMapper){
        this.hashService = service;
        this.userMapper = userMapper;
    }

    public boolean isUsernameAvailable(String username){
        return userMapper.getUser(username) == null;
    }

    public int getUserId(String username){
        return userMapper.getUser(username).getUserId();
    }

    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPass = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.insert(
                new User(
                        null,
                        user.getUsername(),
                        encodedSalt,
                        hashedPass,
                        user.getFirstName(),
                        user.getLastName()
                )
        );
    }
}
