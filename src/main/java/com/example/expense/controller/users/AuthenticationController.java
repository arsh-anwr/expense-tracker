package com.example.expense.controller.users;

import com.example.expense.entity.RefreshTokens;
import com.example.expense.entity.Users;
import com.example.expense.model.users.RefreshTokenRequest;
import com.example.expense.model.users.RegisterUser;
import com.example.expense.model.users.UserDetail;
import com.example.expense.model.users.UserLoginResponse;
import com.example.expense.services.JwtService;
import com.example.expense.services.RefreshTokenService;
import com.example.expense.services.SignedUpUserDetailsService;
import com.example.expense.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    SignedUpUserDetailsService signedUpUserDetailsService;

    @GetMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserDetail userDetail) {

        Users user = userService.getUser(userDetail);

        if (user == null) {
            throw new BadCredentialsException("Username or password is incorrect");
        }


        String accessToken = jwtService.generateToken(user);
        RefreshTokens refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        return ResponseEntity.ok(UserLoginResponse.builder()
                        .accessToken(accessToken)
                        .timeout(jwtService.getExpirationTime())
                        .timeoutType("Milisecond")
                        .refreshToken(refreshToken.getRefreshToken())
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterUser> registerUser(@RequestBody RegisterUser registerUser) {
        userService.storeNewUser(registerUser);
        return ResponseEntity.ok(registerUser);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<UserLoginResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshTokens refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken());

        if (refreshToken == null) {
            throw new BadCredentialsException("Refresh token not found in db");
        }

        refreshTokenService.verifyExpiration(refreshToken);

        Users user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);
        RefreshTokens newRefreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        refreshTokenService.deleteByToken(refreshToken);

        return ResponseEntity.ok(UserLoginResponse.builder()
                .accessToken(accessToken)
                .timeout(jwtService.getExpirationTime())
                .timeoutType("Milisecond")
                .refreshToken(newRefreshToken.getRefreshToken())
                .build());
    }

}
