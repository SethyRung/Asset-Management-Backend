package com.asset_management.securities;

import com.nimbusds.jose.shaded.gson.Gson;
import com.asset_management.enums.ResponseMessageEnum;
import com.asset_management.utils.ResponseBody;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(createErrorBody(accessDeniedException));
    }

    private String createErrorBody(AccessDeniedException accessDeniedException){
        return new Gson().toJson(new ResponseBody<>(ResponseBody.ResponseStatus.builder().code(ResponseMessageEnum.UNAUTHORIZED.code()).errorMessage(ResponseMessageEnum.UNAUTHORIZED.message()).build()));
    }
}
