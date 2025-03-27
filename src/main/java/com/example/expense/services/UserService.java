package com.example.expense.services;

import com.example.expense.entity.Users;
import com.example.expense.model.enums.RoleEnum;
import com.example.expense.model.users.RegisterUser;
import com.example.expense.model.users.UserDetail;
import com.example.expense.repository.RoleRepository;
import com.example.expense.repository.UserRepository;
import com.example.expense.utils.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private EncryptionUtils encryptionUtils;

    public void storeNewUser(RegisterUser registerUser) {
        if (registerUser.getUserName().isEmpty()) return;
        if (registerUser.getPassword().isEmpty()) return;
        if (userRepository.findByUserName(registerUser.getUserName()) != null) return;

        String salt = encryptionUtils.generateSalt();
        try {
            Users users = Users.builder()
                    .userName(registerUser.getUserName())
                    .salt(salt)
                    .password(encryptionUtils.hashPasswordWithSalt(registerUser.getPassword(), salt))
                    .fullName(registerUser.getFullName())
                    .roles(List.of(roleRepository.findByName(RoleEnum.USER).get()))
                    .build();
            userRepository.save(users);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            log.error("Unable to create new user, please try again later", noSuchAlgorithmException.getCause());
        }
    }

    public Users getUser(UserDetail userDetail) {

        Users users = userRepository.findByUserName(userDetail.getUserName());

        if (users == null) {
            return null;
        }

        try {
            if (encryptionUtils.verifyPassword(userDetail.getPassword(), users.getSalt(), users.getPassword())) {
                return users;
            } else {
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("Unable to login");
            return null;
        }

    }
}
