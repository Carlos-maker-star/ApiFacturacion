package com.carlos.apifacturacion.mapper;

import com.carlos.apifacturacion.dto.response.LoginResponse;
import com.carlos.apifacturacion.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "message", constant = "Autenticación exitosa")
    @Mapping(target = "authenticated", constant = "true")
    LoginResponse toLoginResponse(User user);
}
