package com.asset_management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessageEnum implements IResponseMessageEnum {
    OK("0",HttpStatusEnum.OK,""),
    UNAUTHORIZED("1000", HttpStatusEnum.UNAUTHORIZED, "You don't permission to access this resource"),
    BAD_REQUEST("1001", HttpStatusEnum.BAD_REQUEST, "Bad Request"),
    FORBIDDEN("1002", HttpStatusEnum.FORBIDDEN, "Forbidden"),
    INTERNAL_SERVER_ERROR("1003", HttpStatusEnum.INTERNAL_SERVER_ERROR, "Something went wrong"),
    INCORRECT_USERNAME_PASSWORD("1004", HttpStatusEnum.BAD_REQUEST, "Incorrect username and password"),
    INVALID_PARAMETER("1005", HttpStatusEnum.BAD_REQUEST, "Credentials Can't be empty");


    private final String code;
    private final HttpStatusEnum status;
    private final String message;


    @Override
    public String code() {
        return this.code;
    }

    @Override
    public HttpStatusEnum status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
