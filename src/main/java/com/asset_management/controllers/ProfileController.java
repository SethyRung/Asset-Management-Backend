package com.asset_management.controllers;

import com.asset_management.dto.Profile.ChangePasswordDTO;
import com.asset_management.dto.Profile.ProfileReqDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.enums.ResponseMessageEnum;
import com.asset_management.models.User;
import com.asset_management.services.IProfileService;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Profile")
@RestController
@RequestMapping(value = "/api/profile")
@AllArgsConstructor
public class ProfileController {
    private final IProfileService profileService;

    @GetMapping
    public ResponseEntity<ResponseBody<UserResDTO>> getProfile(){
        return ResponseEntity.ok(new ResponseBody<>(profileService.getProfile()));
    }

    @PutMapping
    public ResponseEntity<ResponseBody<UserResDTO>> updateUser(@RequestBody ProfileReqDTO profileReqDTO){
        return ResponseEntity.ok(new ResponseBody<>(profileService.updateProfile(profileReqDTO)));
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<ResponseBody<?>> changePassword( @RequestBody ChangePasswordDTO changePasswordDTO){
        profileService.changePassword(changePasswordDTO);
        return ResponseEntity.ok(new ResponseBody<>(ResponseBody.ResponseStatus.builder().code(ResponseMessageEnum.OK.getCode()).build()));
    }
}
