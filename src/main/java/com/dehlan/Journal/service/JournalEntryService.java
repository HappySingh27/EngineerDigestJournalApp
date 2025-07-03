/*
*
* Controller -> Service -> Repository
* Controller - Only exposes end points, should not have business logic
* Service - Have Business logic
* Repository - Interact with DB using entity/model to perform DB operations
*
* if not required by Business logic,
* never call Repository of one service from another service,
* use methods of respective service to call repository
*
* */

package com.dehlan.Journal.service;

import com.dehlan.Journal.entity.JournalEntry;
import com.dehlan.Journal.entity.User;
import com.dehlan.Journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    /*
    * When a method is annotated with @Transactional,
    * all the operations within that method are executed as a single atomic transaction.
    * If any exception occurs during the execution, the entire transaction is rolled back automatically,
    * ensuring data consistency.
    * You can annotate both classes and methods with @Transactional.
    *
    * In Mongodb @Transactional cannot be implemented without replica
    * */
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){

        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);

            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }
        catch (Exception e){
            throw new RuntimeException("An error occured while saving the entry.", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){

        return journalEntryRepository.findAll();
    }

    public JournalEntry getById(ObjectId id){

        return journalEntryRepository.findById(id).orElse(null);
    }

    public void deleteById(ObjectId Id){

        journalEntryRepository.deleteById(Id);
    }
}
