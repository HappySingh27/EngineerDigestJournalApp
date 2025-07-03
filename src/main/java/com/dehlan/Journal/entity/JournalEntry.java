package com.dehlan.Journal.entity;


// POJO Class

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

// @Document - will tell SpringBoot that this class is mapped with MongoDB collection

@Document(collection = "journal_entries")
@Getter
@Setter
public class JournalEntry {

    @Id  // to make unique key in MongoDb
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;


}
