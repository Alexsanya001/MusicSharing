package com.example.musicsharing.models.dto;

import com.example.musicsharing.validation.annotations.ValidPasswordNew;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestorePasswordRequest {
    @ValidPasswordNew
    private String newPassword;
}
