package com.asset_management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStatusEnum {
    OK("0"),
    UNAUTHORIZED("1000"),
    BAD_REQUEST("1001"),
    FORBIDDEN("1002"),
    INTERNAL_SERVER_ERROR("1003");

    private final String code;
}
