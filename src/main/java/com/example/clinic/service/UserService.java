package com.example.clinic.service;

import com.example.clinic.dto.UserDto;
import com.example.clinic.entity.User;
import com.example.clinic.exception.CommonBaseException;
import com.example.clinic.exception.UserExistExceptionCommon;
import com.example.clinic.handler.Handler;
import com.example.clinic.handler.JwtHandler;
import com.example.clinic.mapper.UserMapper;
import com.example.clinic.message.ExceptionMessage;
import com.example.clinic.message.SimpleMessage;
import com.example.clinic.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleService roleService;


    public User getUserEntityById(Long id) {
        existsUserById(id);
        return userRepository.getById(id);
    }

    public UserDto save(UserDto userDto) throws Exception {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(Handler.hashPassword(userDto.getPassword()))
                .role(roleService.getByNameRole(SimpleMessage.DEFAULT_ROLE))
                .build();

        return userMapper.toDto(userRepository.save(user), JwtHandler.createAuthToken(userDto));
    }

    public UserDto login(UserDto userDto) throws Exception {
        existsUserByUsername(userDto.getUsername());
        User user = userRepository.findByUsername(userDto.getUsername());
        equalHashPasswordUserAndUserDto(user, userDto);
        return userMapper.toDto(user, JwtHandler.createAuthToken(userDto));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (Objects.isNull(user))
            throw new UsernameNotFoundException(String.format(ExceptionMessage.USER_EXIST_WITH_USERNAME, s));
        return user;
    }


    public void existsUserById(Long id) {
        if (!userRepository.existsUserById(id))
            throw new CommonBaseException(String.format(ExceptionMessage.USER_NOT_EXIST_WITH_ID, id));
    }

    public void existsUserByUsername(String username) {
        if (!userRepository.existsUserByUsername(username))
            throw new UserExistExceptionCommon(String.format(ExceptionMessage.USER_EXIST_WITH_USERNAME, username));
    }

    public void equalHashPasswordUserAndUserDto(User user, UserDto userDto) throws Exception {
        if (!user.getPassword().equals(Handler.hashPassword(userDto.getPassword())))
            throw new CommonBaseException(ExceptionMessage.LOGIN_OR_PASSWORD_IS_NOT_CORRECT);
    }

}
