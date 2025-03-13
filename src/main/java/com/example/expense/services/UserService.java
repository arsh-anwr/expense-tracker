package com.example.expense.services;

import com.example.expense.entity.Users;
import com.example.expense.model.users.RegisterUser;
import com.example.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void storeUser(RegisterUser registerUser) {
        if (registerUser.getUserName().isEmpty()) return;
        if (registerUser.getPassword().isEmpty()) return;
        if (userRepository.findByUserName(registerUser.getUserName()) != null) return;

        Users users = Users.builder()
                .userName(registerUser.getUserName())
                .password(registerUser.getPassword())
                .fullName(registerUser.getFullName())
                .build();

        userRepository.save(users);
    }

    public Users getUser(String userName) {
        return userRepository.findByUserName(userName);
    }
}
