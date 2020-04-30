package com.openpayd.exchanger.http;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends Response{
    private String message;

    public ErrorResponse(String message) {
        super();
        this.message = message;
    }
}
