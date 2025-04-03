package com.example.expense.model.users;

import com.example.expense.model.enums.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    String userName;
    String password;
    LoginType loginType;
}