package com.dehlan.Journal.controller;


import com.dehlan.Journal.entity.JournalEntry;
import com.dehlan.Journal.entity.User;
import com.dehlan.Journal.service.JournalEntryService;
import com.dehlan.Journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    /*
    * Methods inside a controller class should be public,
    * so that they can be accessed and invoked by the spring framework
    * or external HTTP requests
    *
    * Controller call service,
    * service call repository, repository is an interface
    * repository extends MongoRepository
    *
    * Business logic would be inside service, service is a java class
    * Controller will only expose end points
    * Repository have methods to interact with MongoDB
    *
    * ResponseEntity provides methods for setting the response status, headers, and body.
    * You can use it to return different types of data in your controller methods, such as JSON,XML or even HTML
    * */

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();

        if(all != null && !all.isEmpty()){

            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /*

This method has been updated by implementing ResponseEntity,
Will remove this in future if needed
for now keeping as a note to go through what I had studied

    @GetMapping("/id/{Id}")
    public JournalEntry getJournalById(@PathVariable ObjectId Id){

        return journalEntryService.getById(Id);

    }

 */

    @GetMapping("/id/{Id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId Id){

        JournalEntry entry = journalEntryService.getById(Id);

        if(entry!=null){
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){

        /*
        * @RequestBody - it's like saying hey spring please take the data from the request
        * and turn it into a java object of type "JournalEntry" that I can use in my java code
        * */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try{
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId,@PathVariable String userName, @RequestBody JournalEntry myEntry){

        User user = userService.findByUserName(userName);

        if(user!=null){

            List<JournalEntry> journalEntries = user.getJournalEntries();
            Optional<JournalEntry> old = journalEntries.stream().filter(x -> x.getId().equals(myId)).findFirst();

            //JournalEntry old = journalEntryService.getById(myId);

            /*
             * Only variable which is sent as JSON from client would be updated
             * therefore we have put validation for null and empty
             * Suppose client has only sent title for any id , so only title would be updated,
             * and content will remain as it is (existing one)
             * */
            if(old.isPresent()){
                JournalEntry journalEntry = old.get();
                journalEntry.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : journalEntry.getTitle());
                journalEntry.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("") ? myEntry.getContent() : journalEntry.getContent());
                journalEntryService.saveEntry(journalEntry);

                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /*
    * return type of deleteJournalById -> ResponseEntity<?>
    * ? - wild card character, now ResponseEntity can return any type,
    * It has become more flexible now
    * */
    @DeleteMapping("/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String userName, @PathVariable ObjectId myId){

        User user = userService.findByUserName(userName);

        if(user!=null){

            List<JournalEntry> journalEntries = user.getJournalEntries();
            Optional<JournalEntry> old = journalEntries.stream().filter(x -> x.getId().equals(myId)).findFirst();

            //JournalEntry old = journalEntryService.getById(myId);

            if(old.isPresent()){
                JournalEntry journalEntry = old.get();
                journalEntryService.deleteById(journalEntry.getId());
                journalEntries.removeIf(x -> x.equals(journalEntry));
                user.setJournalEntries(journalEntries);
                userService.saveEntry(user);

                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
