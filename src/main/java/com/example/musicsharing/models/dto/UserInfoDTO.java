package com.example.musicsharing.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserInfoDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
