package com.dehlan.Journal.repository;

import com.dehlan.Journal.entity.JournalEntry;
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


public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
