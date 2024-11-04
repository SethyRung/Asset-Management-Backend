package com.asset_management.utils;

import com.asset_management.enums.ResponseMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.UUID;

@Data
public class ResponseBody<T> {
    private final static String OK = "0";

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseStatus {
        private String code;
        private String errorCode;
        private String errorMessage;
        private String warningMessage;
        @Builder.Default
        private String requestId = UUID.randomUUID().toString();
        @Builder.Default
        private Long requestTime = Calendar.getInstance().getTimeInMillis();
    }

    private ResponseStatus status;
    private T data;

    public ResponseBody() {
        this.status = ResponseStatus.builder().code(OK).build();
        this.data = null;
    }

    public ResponseBody(ResponseStatus status) {
        this.status = status;
    }

    public ResponseBody(T data) {
        this.status = ResponseStatus.builder().code(OK).build();
        this.data = data;
    }

    public ResponseBody(ResponseStatus status, T data) {
        this.data = data;
        this.status = ResponseStatus.builder().code(status.getCode()).errorCode(status.getErrorCode()).errorMessage(status.getErrorMessage()).warningMessage(status.getWarningMessage()).build();
    }

    public ResponseBody(ResponseMessageEnum error) {
        this.status = ResponseStatus.builder().code(error.code()).errorCode(error.getStatus().toString()).errorMessage(error.getMessage()).build();
    }
}
