package com.example.expense.repository;

import com.example.expense.entity.RefreshTokens;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshTokens, ObjectId> {
    RefreshTokens findByRefreshToken(String refreshToken);
}
