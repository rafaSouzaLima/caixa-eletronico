package com.api.caixa.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.caixa.dtos.UserLoginDto;
import com.api.caixa.dtos.UserRecordDto;
import com.api.caixa.dtos.UserResponseDto;
import com.api.caixa.models.UserModel;
import com.api.caixa.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRecordDto userRecordDto) {
        try {
            UserResponseDto userResponse = userService.saveUser(userRecordDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto userLoginDto) {
        UserModel user = userService.findUser(userLoginDto.nome(), userLoginDto.senha());
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("message", "Usuário está logado");
            if (user.getConta() != null) {
                response.put("numeroConta", user.getConta().getNumero());
            } else {
                response.put("numeroConta", "Número da conta não disponível");
            }
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Nome de usuário ou senha incorretos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
