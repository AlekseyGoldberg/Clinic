package com.example.clinic.mapper;

import com.example.clinic.dto.UserDto;
import com.example.clinic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(ignore = true, target = "password")
    @Mapping(source = "jwt", target = "jwt")
    @Mapping(source = "user.role.role", target = "role")
    UserDto toDto(User user, String jwt);
}
