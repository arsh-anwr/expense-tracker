package com.example.expense.provider;

import com.example.expense.entity.Users;
import com.example.expense.repository.UserRepository;
import com.example.expense.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class AuthenticationForUserProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EncryptionUtils encryptionUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Users userOptional = userRepository.findByUserName(username);
        if (userOptional == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Users user = userOptional;
        String salt = user.getSalt();

        String hashedPassword = null;
        try {
            hashedPassword = encryptionUtils.hashPasswordWithSalt(password, salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        if (hashedPassword.equals(user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}