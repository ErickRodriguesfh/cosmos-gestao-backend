package br.ebr.cosmos.cosmos_gestao.adapters.inbound.controller;

import br.ebr.cosmos.cosmos_gestao.application.dto.LoginRequestDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.LoginResponseDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.UserDTO;
import br.ebr.cosmos.cosmos_gestao.application.usecases.UserUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserUseCases userUseCases;

    public AuthController(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userUseCases.login(loginRequestDTO));
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
        userUseCases.register(newUser);
    }

}
