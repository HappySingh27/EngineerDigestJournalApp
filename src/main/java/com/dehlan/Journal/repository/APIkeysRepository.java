package com.dehlan.Journal.repository;

import com.dehlan.Journal.entity.APIkeys;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface APIkeysRepository extends MongoRepository<APIkeys, ObjectId> {
}
