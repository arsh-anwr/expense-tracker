package com.example.expense.controller.users;

import com.example.expense.entity.Users;
import com.example.expense.model.users.RegisterUser;
import com.example.expense.model.users.UserDetail;
import com.example.expense.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Users loginUser(@RequestBody UserDetail userDetail) {
        return userService.getUser(userDetail.getUserName());

    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterUser> registerUser(@RequestBody RegisterUser registerUser) {
        userService.storeUser(registerUser);
        return ResponseEntity.ok(registerUser);
    }

}
