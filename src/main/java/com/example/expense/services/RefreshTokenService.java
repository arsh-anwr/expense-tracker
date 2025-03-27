package com.example.expense.services;

import com.example.expense.entity.RefreshTokens;
import com.example.expense.entity.Users;
import com.example.expense.repository.RefreshTokenRepository;
import com.example.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${security.jwt.refresh-token.expiration-time}")
    private long jwtRefreshExpiration;

    @Autowired
    private JwtService jwtService;

    public RefreshTokens createRefreshToken(String username){
        Users user = userRepository.findByUserName(username);
        RefreshTokens refreshToken = RefreshTokens.builder()
                .user(user)
                .refreshToken(jwtService.generateToken(user))
                .expiredDate(Instant.now().plusMillis(jwtRefreshExpiration)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }



    public RefreshTokens findByToken(String token){
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public void deleteByToken(RefreshTokens refreshToken){
        refreshTokenRepository.delete(refreshToken);
    }

    public RefreshTokens verifyExpiration(RefreshTokens token){
        if(token.getExpiredDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}
