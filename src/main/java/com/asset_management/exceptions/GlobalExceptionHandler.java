package com.asset_management.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.enums.ResponseMessageEnum;
import com.asset_management.utils.ErrorField;
import com.asset_management.utils.ResponseBody;
import com.asset_management.utils.ResponseBody.ResponseStatus;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MissingServletRequestParameterException.class, MissingPathVariableException.class})
    public ResponseEntity<ResponseBody<?>> handleMissingRequireRequestParams(MissingPathVariableException ex){
        log.error(ex.getMessage());
        ResponseStatus status = ResponseStatus.builder()
                .code(ResponseMessageEnum.BAD_REQUEST.getCode())
                .errorCode(ResponseMessageEnum.BAD_REQUEST.getStatus().toString())
                .errorMessage("Required parameter is not present")
                .build();
        return ResponseEntity.ok(new ResponseBody<>(status));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseBody<?>> handleMissingRequireRequestBody(HttpMessageNotReadableException ex){
        log.error(ex.getMessage(), ex);
        ResponseStatus status = ResponseStatus.builder()
                .code(ResponseMessageEnum.BAD_REQUEST.getCode())
                .errorCode(ResponseMessageEnum.BAD_REQUEST.getStatus().toString())
                .errorMessage("Required Request Body is not present")
                .build();
        return ResponseEntity.ok(new ResponseBody<>(status));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<?>> handleException (Exception ex){
        log.error(ex.getMessage());
        ResponseStatus status = ResponseStatus.builder()
                .code(ResponseMessageEnum.INTERNAL_SERVER_ERROR.getCode())
                .errorCode(ResponseMessageEnum.INTERNAL_SERVER_ERROR.getStatus().toString())
                .errorMessage(ResponseMessageEnum.INTERNAL_SERVER_ERROR.getMessage())
                .build();
        return ResponseEntity.ok(new ResponseBody<>(status));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseBody<?>> handleValidationException (ValidationException ex){
        ObjectMapper objectMapper = new ObjectMapper();
        List<ErrorField> errorFields;
        try{
            errorFields = objectMapper.readValue(ex.getMessage(), new TypeReference<List<ErrorField>>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new ErrorException(HttpStatusEnum.INTERNAL_SERVER_ERROR, "Fail to deserialize to Java Object!");
        }
        log.error(ex.getMessage());
        ResponseStatus status = ResponseStatus.builder()
                .code(ResponseMessageEnum.BAD_REQUEST.getCode())
                .errorCode(ResponseMessageEnum.BAD_REQUEST.getStatus().toString())
                .errorMessage(ResponseMessageEnum.BAD_REQUEST.getMessage())
                .build();
        return ResponseEntity.ok(new ResponseBody<>(status, errorFields));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseBody<?>> handleException(ApplicationException ex){
        log.error(ex.getResponseBody().toString());
        return ResponseEntity.ok(ex.getResponseBody());
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ResponseBody<?>> handleErrorException (ErrorException ex){
        log.error(ex.getErrorMessage());
        ResponseStatus status = ResponseStatus.builder()
                .code(ResponseMessageEnum.BAD_REQUEST.getCode())
                .errorCode(ResponseMessageEnum.BAD_REQUEST.getStatus().toString())
                .errorMessage(ex.getErrorMessage().isEmpty() ? "Something went wrong" : ex.getErrorMessage())
                .build();
        return ResponseEntity.ok(new ResponseBody<>(status));
    }
}
