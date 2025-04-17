package com.example.expense.controller.producer;

import com.example.expense.entity.Transactions;
import com.example.expense.services.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> check(@RequestBody Transactions transactions) {
        kafkaProducer.sendTransaction(transactions);
        return ResponseEntity.ok("Success");
    }
}
