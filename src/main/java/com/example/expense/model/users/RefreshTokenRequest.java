package com.example.expense.model.users;

import lombok.Data;
import lombok.NonNull;

@Data
public class RefreshTokenRequest {
    @NonNull
    String refreshToken;
}
