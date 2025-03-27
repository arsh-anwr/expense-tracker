package com.example.expense.model.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponse {
    String accessToken;
    String refreshToken;
    long timeout;
    String timeoutType;
}
