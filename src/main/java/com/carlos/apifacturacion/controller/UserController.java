package com.carlos.apifacturacion.controller;

import com.carlos.apifacturacion.dto.request.LoginRequest;
import com.carlos.apifacturacion.dto.response.LoginResponse;
import com.carlos.apifacturacion.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiFA/users")
@CrossOrigin(origins = "${app.cors.allowed-origin}")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
