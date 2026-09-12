package com.carlos.apifacturacion.service.impl;

import com.carlos.apifacturacion.dto.request.LoginRequest;
import com.carlos.apifacturacion.dto.response.LoginResponse;
import com.carlos.apifacturacion.entity.User;
import com.carlos.apifacturacion.mapper.UserMapper;
import com.carlos.apifacturacion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!user.isEnabled()) {
            throw new RuntimeException("El usuario se encuentra inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return userMapper.toLoginResponse(user);
    }
}
