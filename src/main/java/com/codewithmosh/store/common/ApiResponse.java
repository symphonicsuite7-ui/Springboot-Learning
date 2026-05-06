package com.codewithmosh.store.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {

        return new ApiResponse<>(0, "Success", data);

    }

    public static <T> ApiResponse<T> error(int code , String message){
        return new ApiResponse<>(code, message, null);
    }
}
