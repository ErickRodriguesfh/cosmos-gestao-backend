package br.ebr.cosmos.cosmos_gestao.infrastructure.config.security;

import br.ebr.cosmos.cosmos_gestao.application.dto.LoginRequestDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.LoginResponseDTO;
import br.ebr.cosmos.cosmos_gestao.application.usecases.RoleUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.role.Role;
import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import br.ebr.cosmos.cosmos_gestao.domain.user.UserRepository;
import br.ebr.cosmos.cosmos_gestao.infrastructure.config.security.util.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AuthService {

    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleUseCases roleUseCases;

    public AuthService(JwtUtils jwtUtils, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, RoleUseCases roleUseCases) {
        this.jwtUtils = jwtUtils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleUseCases = roleUseCases;
    }

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password()));
        } catch (AuthenticationException exception) {
            return null;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var userDetails = (UserDetails) authentication.getPrincipal();
        var jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        var user = userRepository.findByUsername(loginRequestDTO.username())
                .orElse(null);

        return new LoginResponseDTO(jwtToken, user.getName(), user.getId(), roles);
    }

    @Transactional
    public void register(User newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }

        var role = roleUseCases.findByName(Role.Values.BASIC.name());
        var user = new User();
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setSector(newUser.getSector());
        user.setUsername(newUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        user.setRoles(Set.of(role));
        user.setImageUrl(newUser.getImageUrl());

        userRepository.save(user);
    }

}
