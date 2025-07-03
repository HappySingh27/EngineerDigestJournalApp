package com.dehlan.Journal.controller;

import com.dehlan.Journal.entity.User;
import com.dehlan.Journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
*
* @RestController = @Controller + @ResponseBody.
* It is used for REST APIs and automatically serializes return values to JSON/XML.
* @Controller is used for traditional MVC apps where you return view names (e.g., Thymeleaf templates).
* Use @RestController for APIs, and @Controller for web pages.
*
*/

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch(DuplicateKeyException e){
            //e.printStackTrace();
            return new ResponseEntity<>("User already exists",HttpStatus.BAD_REQUEST);
        }

    }
}
