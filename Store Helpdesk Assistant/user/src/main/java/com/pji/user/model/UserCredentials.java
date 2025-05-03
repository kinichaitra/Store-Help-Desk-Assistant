package com.pji.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

    private String token;
    private String name;
    private String accessLevel;
    private String userName;
}
