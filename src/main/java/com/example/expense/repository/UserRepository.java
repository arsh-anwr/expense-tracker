package com.example.expense.repository;

import com.example.expense.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

public interface UserRepository extends MongoRepository<Users, ObjectId> {
    Users findByUserName(String userName);
}
