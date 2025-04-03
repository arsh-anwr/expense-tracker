package com.example.expense.controller.users;

import com.example.expense.entity.RefreshTokens;
import com.example.expense.entity.Users;
import com.example.expense.model.enums.LoginType;
import com.example.expense.model.google.GoogleProfileResponse;
import com.example.expense.model.users.*;
import com.example.expense.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    GoogleService googleService;

    @Autowired
    SignedUpUserDetailsService signedUpUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody LoginUserDTO userDetail) {

        if (userDetail.getLoginType().equals(LoginType.GOOGLE)) {
            return googleLoginUser(userDetail);
        }

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

    private ResponseEntity<UserLoginResponse> googleLoginUser(LoginUserDTO userDetail) {

        String googleAuthToken = googleService.getGoogleAuth(userDetail);
        GoogleProfileResponse googleProfileResponse = googleService.getProfileDetailsGoogle(googleAuthToken);

        Users user = userService.getUser(googleProfileResponse.getEmail());

        if (user == null) {
            userService.storeNewGoogleUser(googleProfileResponse);
            user = userService.getUser(googleProfileResponse.getEmail());
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


    @GetMapping("/glogin")
    public  ResponseEntity<?> gmailLogin(@RequestParam("code") String code, @RequestParam("scope") String scope) {
        return ResponseEntity.ok("Token Generated");
    }

}
