package com.asset_management.controllers;

import com.asset_management.dto.User.ChangePasswordDTO;
import com.asset_management.dto.User.UserResponseDTO;
import com.asset_management.enums.ResponseMessageEnum;
import com.asset_management.enums.RoleEnum;
import com.asset_management.models.User;
import com.asset_management.services.IUserService;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping(value = "/api/user")
@AllArgsConstructor
public class UserController {
    private IUserService userService;

    @GetMapping
    public ResponseEntity<ResponseBody<List<UserResponseDTO>>> getUsers(@RequestParam(value = "role", required = false) RoleEnum role){
        return ResponseEntity.ok(new ResponseBody<>(userService.getUserList(role)));
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<ResponseBody<?>> changePassword(@AuthenticationPrincipal User user, @RequestBody ChangePasswordDTO changePasswordDTO){
        userService.changePassword(user, changePasswordDTO);
        return ResponseEntity.ok(new ResponseBody<>(ResponseBody.ResponseStatus.builder().code(ResponseMessageEnum.OK.getCode()).build()));
    }
}