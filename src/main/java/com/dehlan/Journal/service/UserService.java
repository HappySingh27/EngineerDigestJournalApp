package com.dehlan.Journal.service;


// Controller -> Service -> repository

import com.dehlan.Journal.entity.JournalEntry;
import com.dehlan.Journal.entity.User;
import com.dehlan.Journal.repository.JournalEntryRepository;
import com.dehlan.Journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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
