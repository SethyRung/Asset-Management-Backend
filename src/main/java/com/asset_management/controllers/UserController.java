package com.asset_management.controllers;

import com.asset_management.dto.User.ChangePasswordDTO;
import com.asset_management.dto.User.UserReqDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.enums.ResponseMessageEnum;
import com.asset_management.models.User;
import com.asset_management.services.IUserService;
import com.asset_management.utils.PaginationPage;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users")
@RestController
@RequestMapping(value = "/api/users")
@AllArgsConstructor
public class UserController {
    private IUserService userService;

    @GetMapping
    public ResponseEntity<ResponseBody<PaginationPage<UserResDTO>>> getUsers(@RequestParam(required = false) String search,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResponseBody<>(userService.getUserList(search, page, size)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBody<UserResDTO>> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(new ResponseBody<>(userService.getUserById(id)));
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<ResponseBody<?>> changePassword(@AuthenticationPrincipal User user, @RequestBody ChangePasswordDTO changePasswordDTO){
        userService.changePassword(user, changePasswordDTO);
        return ResponseEntity.ok(new ResponseBody<>(ResponseBody.ResponseStatus.builder().code(ResponseMessageEnum.OK.getCode()).build()));
    }

    @PostMapping
    public ResponseEntity<ResponseBody<UserResDTO>> addUser(@RequestBody UserReqDTO userReqDTO){
        return ResponseEntity.ok(new ResponseBody<>(userService.addUser(userReqDTO)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBody<UserResDTO>> updateUser(@PathVariable Long id, @RequestBody UserReqDTO userReqDTO){
        return ResponseEntity.ok(new ResponseBody<>(userService.updateUser(id, userReqDTO)));
    }

    public ResponseEntity<ResponseBody<?>> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseBody<>());
    }
}