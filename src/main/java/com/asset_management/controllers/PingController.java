package com.asset_management.controllers;

import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ping")
@RestController()
@RequestMapping("/api")
public class PingController {
    @GetMapping("/ping")
    public ResponseEntity<ResponseBody<String>> ping(){
        return ResponseEntity.ok(new ResponseBody<>("Pong"));
    }
}
