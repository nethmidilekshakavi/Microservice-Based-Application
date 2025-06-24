// path: com.example.vehiclservice.dto.UserDto.java

package com.example.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String image;
    private boolean isActive;

}
