package com.example.expense.entity;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Document(collection = "refreshTokens")
@Data
@Builder
public class RefreshTokens {
    @Id
    ObjectId id;
    String refreshToken;
    Instant expiredDate;
    @DBRef
    Users user;
}
