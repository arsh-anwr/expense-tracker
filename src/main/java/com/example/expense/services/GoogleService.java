package com.example.expense.services;

import com.example.expense.model.google.GoogleAuthResponse;
import com.example.expense.model.google.GoogleProfileResponse;
import com.example.expense.model.users.LoginUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleService {
    @Value("${google.security.client-id}")
    private String googleClientId;
    @Value("${google.security.client-secret}")
    private String googleClientSecret;
    @Value("${google.security.redirect_uri}")
    private String googleTokenRedirectUri;
    @Value("${google.token.uri}")
    private String googleTokenUri;
    @Value("${google.user.info.uri}")
    private String googleUserInfoUri;

    public String getGoogleAuth(LoginUserDTO loginUserDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", loginUserDTO.getCode());
        params.add("redirect_uri", googleTokenRedirectUri);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email");
        params.add("scope", "openid");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        GoogleAuthResponse response = restTemplate.postForObject(googleTokenUri, requestEntity, GoogleAuthResponse.class);
        return response.getAccessToken();
    }

    public GoogleProfileResponse getProfileDetailsGoogle(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<GoogleProfileResponse> response = restTemplate.exchange(googleUserInfoUri, HttpMethod.GET, requestEntity, GoogleProfileResponse.class);
        return response.getBody();
    }
}
