package com.example.expense.services.kafka;

import com.example.expense.entity.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Transactions> kafkaTemplate;

    public void sendTransaction(Transactions transactions) {
        kafkaTemplate.send("transactions", transactions);
    }
}
