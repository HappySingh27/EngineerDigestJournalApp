package com.dehlan.Journal.repository;

import com.dehlan.Journal.entity.JournalEntry;
import com.dehlan.Journal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/*
*
* MongoRepository is a genric Interface which has method for CRUD operations in mongoDB
* It takes 2 genric parameters
* first - simple POJO class on which we would do operations
* second - ID field of that POJO class
*
* An interface that extends MongoRepository<C, I> is automatically registered as a Spring bean by Spring Data MongoDB,
* so we don’t need to annotate it explicitly with @Component, @Repository, or any other stereotype annotation.
* */


public interface UserRepository extends MongoRepository<User, ObjectId> {

    /*
     * ✅ Spring Data Query Method: findByUserName(String userName)
     *
     * - Spring auto-generates the query: { "userName": userName }
     * - No manual implementation needed — handled via method name parsing.
     * - Works because 'UserRepository' extends MongoRepository.
     * - Follows naming convention: findBy + exact field name (case-sensitive).
     * - Spring creates a proxy at runtime to implement this.
     * - Common operators: And, Or, In, GreaterThan, Containing, IgnoreCase, etc.
     */


    User findByUserName(String username);
    User deleteByUserName(String username);
}
