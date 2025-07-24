package com.dehlan.Journal.service;


// Controller -> Service -> repository

import com.dehlan.Journal.entity.JournalEntry;
import com.dehlan.Journal.entity.User;
import com.dehlan.Journal.repository.JournalEntryRepository;
import com.dehlan.Journal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/*
 * 🔍 grep -r "@Slf4j" src/
 *
 * ✅ What it does:
 * Searches recursively through all files in the `src/` directory
 * for lines that contain the Lombok annotation `@Slf4j`.
 *
 * 📘 Explanation:
 * - grep      → command-line tool to search for patterns in text
 * - -r        → recursive search (includes subfolders)
 * - "@Slf4j"  → the exact annotation string you're looking for
 * - src/      → target directory to begin searching (typically your source folder)
 */
@Slf4j // this annotation is part of lombok we do not need to create class we can directly log loggers
@Component
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveEntry(User user){

        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(ObjectId id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(ObjectId Id){
        userRepository.deleteById(Id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public User deleteByUserName(String username){
        return userRepository.deleteByUserName(username);
    }

}
