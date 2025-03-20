package com.example.expense.controller.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ExampleController {

    @GetMapping
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("Success");
    }
}
