package com.asset_management.exceptions;

import com.asset_management.utils.ResponseBody;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ResponseBody<?> responseBody;
    public ApplicationException(ResponseBody<?> responseBody) {
        super();
        this.responseBody = responseBody;
    }
}
