package com.asset_management.exceptions;

import com.asset_management.enums.HttpStatusEnum;
import lombok.Getter;

@Getter
public class ErrorException extends RuntimeException {
    private final HttpStatusEnum httpStatusEnum;
    private final String errorMessage;
    public ErrorException(HttpStatusEnum httpStatusEnum,String errorMessage) {
        super();
        this.httpStatusEnum = httpStatusEnum;
        this.errorMessage = errorMessage;
    }
}
