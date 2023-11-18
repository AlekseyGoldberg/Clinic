package com.example.clinic.controller;

import com.example.clinic.dto.UserDto;
import com.example.clinic.text.LoggingMessage;
import com.example.clinic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Log4j2
public class UserController {
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody UserDto userDto) throws Exception {
        log.info(LoggingMessage.REGISTRATION_USER_WITH_USERNAME, userDto.getUsername());
        return ResponseEntity.ok(userService.save(userDto));
    }


    @PostMapping("/login")
    public ResponseEntity<UserDto> refreshToken(@RequestBody UserDto userDto) throws Exception {
        log.info(LoggingMessage.LOGIN_USER_WITH_USERNAME, userDto.getUsername());
        return ResponseEntity.ok(userService.login(userDto));
    }
}
