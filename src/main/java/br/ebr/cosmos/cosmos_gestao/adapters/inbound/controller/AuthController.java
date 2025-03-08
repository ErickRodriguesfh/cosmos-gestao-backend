package br.ebr.cosmos.cosmos_gestao.adapters.inbound.controller;

import br.ebr.cosmos.cosmos_gestao.application.dto.LoginRequestDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.LoginResponseDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.UserDTO;
import br.ebr.cosmos.cosmos_gestao.application.usecases.UserUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import br.ebr.cosmos.cosmos_gestao.infrastructure.config.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        var response = authService.authenticateUser(loginRequestDTO);
        if (Objects.isNull(response)) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserDTO userDTO) {
        var newUser = new User();
        newUser.setName(userDTO.name());
        newUser.setPassword(userDTO.password());
        newUser.setSector(userDTO.sector());
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());
        newUser.setImageUrl(userDTO.imageUrl());
        authService.register(newUser);
    }

}
