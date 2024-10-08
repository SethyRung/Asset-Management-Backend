package com.asset_management.exceptions;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.utils.ErrorField;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ValidationException extends RuntimeException {

    private final BindingResult errors;

    public String getValidationMessage(BindingResult bindingResult) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ErrorField> errorFields = new ArrayList<>();
        String errorsInJson = null;

        for(ObjectError objectError : bindingResult.getAllErrors()){
            if(objectError instanceof FieldError fieldError){
                errorFields.add(new ErrorField(fieldError.getField(), fieldError.getDefaultMessage()));
            }
        }

        try {
            errorsInJson = objectMapper.writeValueAsString(errorFields);
            return errorsInJson;
        }catch (JsonProcessingException e){
            log.error(e.getMessage());
            throw new ErrorException((HttpStatusEnum.INTERNAL_SERVER_ERROR), "Fail to serialize error into string!");
        }
    }
    public String getMessages(){
        return getValidationMessage(this.errors);
    }

    @Override
    public  String getMessage(){
        return this.getMessages();
    }
}
