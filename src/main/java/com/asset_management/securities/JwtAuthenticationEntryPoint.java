package com.asset_management.securities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.asset_management.enums.ResponseMessageEnum;
import com.asset_management.utils.ResponseBody;
import com.asset_management.utils.ResponseBody.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(createErrorBody(authException));
    }

    private String createErrorBody(AuthenticationException authenticationException) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseStatus responseStatus = ResponseStatus.builder().code(ResponseMessageEnum.UNAUTHORIZED.getCode()).errorMessage(ResponseMessageEnum.UNAUTHORIZED.getMessage()).build();
        ResponseBody<?> responseBody = new ResponseBody<>(responseStatus);
        return  objectMapper.writeValueAsString(responseBody);
    }
}
