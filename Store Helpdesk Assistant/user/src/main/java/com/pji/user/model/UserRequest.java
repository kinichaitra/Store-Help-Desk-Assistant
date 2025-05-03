package com.pji.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserRequest {
    @NotBlank(message = "Username cannot be empty")
    @Size(min=4,max=25,message="Username length should be from 4 to 25 characters")
    private String username;

    @NotBlank(message = "password cannot be empty")
    @Size(min=8,max=25,message="Password length should be from 4 to 25 characters")
    private String password;
}
