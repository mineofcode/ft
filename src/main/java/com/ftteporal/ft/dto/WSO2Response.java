package com.ftteporal.ft.dto;

import lombok.Data;

@Data
public class WSO2Response {
    private String statusCode;
    private String status;
    private String message;
    private String dataResponse;
}
