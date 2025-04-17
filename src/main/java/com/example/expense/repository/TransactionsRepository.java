package com.example.expense.repository;

import com.example.expense.entity.Transactions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionsRepository extends MongoRepository<Transactions, ObjectId> {
}
