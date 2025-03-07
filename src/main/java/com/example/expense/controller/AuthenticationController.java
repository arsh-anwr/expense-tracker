package com.example.expense.controller;


import com.example.expense.model.RegisterUser;
import com.example.expense.model.UserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    HashMap<String, UserDetail> map = new HashMap<>();

    @PostMapping("/login")
    public UserDetail loginUser(@RequestBody UserDetail userDetail){
        return map.get(userDetail.getUserName());
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterUser> registerUser(@RequestBody RegisterUser registerUser){
        if(map.containsKey(registerUser.getUserName())){
            return ResponseEntity.badRequest().build();
        }

        map.put(registerUser.getUserName(), UserDetail.builder()
                .userName(registerUser.getUserName())
                .password(registerUser.getPassword()).build());
        return ResponseEntity.ok(registerUser);
    }
}
