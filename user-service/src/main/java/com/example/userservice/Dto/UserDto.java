package com.example.userservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String role;

}
