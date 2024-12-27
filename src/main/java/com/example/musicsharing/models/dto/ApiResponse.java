package com.example.musicsharing.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean isSuccess;
    private T data;
    private List<ErrorDetail> errors;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<?> failure(List<ErrorDetail> errors) {
        return new ApiResponse<>(false, null, errors);
    }
}
