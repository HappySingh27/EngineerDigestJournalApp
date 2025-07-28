package com.dehlan.Journal.repository;

import com.dehlan.Journal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

/*
* This class is for writing custom queries
*
* */
@Component
public class UserRepositoryCreteria {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){

        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"),
                        Criteria.where("sentimentAnalysis").is(true)
                )
        );


        List<User> userList = mongoTemplate.find(query, User.class);

        return userList;
        }
}
