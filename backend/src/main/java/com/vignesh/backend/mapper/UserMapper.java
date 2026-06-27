package com.vignesh.backend.mapper;

import com.vignesh.backend.dto.request.RegisterRequest;
import com.vignesh.backend.dto.response.RegisterResponse;
import com.vignesh.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest request);

    @Mapping(target = "message", constant = "Registration successful")
    RegisterResponse toResponse(User user);

}