package com.dat.backend_version_2.dto;

import lombok.Data;

@Data
public class RestResponse<T> {
    private int statusCode;
    private String error;

    private Object message;
    private T data;
}
