package com.example.expense.entity;

import com.example.expense.model.enums.transactions.Category;
import com.example.expense.model.enums.transactions.TransactionMode;
import com.example.expense.model.enums.transactions.TransactionType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
    @Id
    private ObjectId id;
    @NonNull
    private String transactionId;
    @NonNull
    private String userName;
    private Instant date;
    @NonNull
    private Double amount;
    @NonNull
    private TransactionMode mode;
    @NonNull
    private Category category;
    private String description;
    @NonNull
    private TransactionType transactionType;
}
