package com.example.expense.services.kafka;

import com.example.expense.entity.Transactions;
import com.example.expense.repository.TransactionsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@KafkaListener(id = "kafka-consumer-listener-id", topics = "#{'${spring.kafka.incoming.topic}'.split(',')}")
public class KafkaConsumer {

    @Autowired
    TransactionsRepository transactionsRepository;

    @KafkaHandler
    public void consumerTransactions(String transactionData) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Transactions transactions = objectMapper.readValue(transactionData, Transactions.class);
            transactions.setDate(Instant.now());
            transactionsRepository.save(transactions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
