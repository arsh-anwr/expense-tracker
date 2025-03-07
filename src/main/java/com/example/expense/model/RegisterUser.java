package com.example.expense.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterUser extends UserDetail{
    String fullName;
}
