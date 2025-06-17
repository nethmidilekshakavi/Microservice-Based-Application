package com.example.userservice.Security.Secure;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignUp {
    @NotNull(message = "email cannot be null")
    private String email;
    @NotNull(message = "weak password")
    private String password;
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "role cannot be null")
    private String role ;
    @NotNull(message = "proPic cannot be null")
    private String image;



}
