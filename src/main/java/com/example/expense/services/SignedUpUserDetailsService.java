package com.example.expense.services;

import com.example.expense.entity.Users;
import com.example.expense.repository.UserRepository;
import com.example.expense.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class SignedUpUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username);
    }

    public Users loadUser(String username) {
        return userRepository.findByUserName(username);
    }
}
