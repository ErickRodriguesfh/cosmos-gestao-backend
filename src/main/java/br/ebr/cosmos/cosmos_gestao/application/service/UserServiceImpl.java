package br.ebr.cosmos.cosmos_gestao.application.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.ebr.cosmos.cosmos_gestao.adapters.outbound.entities.JpaRoleEntity;
import br.ebr.cosmos.cosmos_gestao.application.dto.LoginRequestDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.LoginResponseDTO;
import br.ebr.cosmos.cosmos_gestao.application.usecases.RoleUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.role.Role;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.ebr.cosmos.cosmos_gestao.application.usecases.UserUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import br.ebr.cosmos.cosmos_gestao.domain.user.UserRepository;

@Service
public class UserServiceImpl implements UserUseCases {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtEncoder jwtEncoder;
    private final RoleUseCases roleUseCases;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtEncoder jwtEncoder, RoleUseCases roleUseCases) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.roleUseCases = roleUseCases;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var user = userRepository.findByUsername(loginRequestDTO.username())
                .orElseThrow(() -> new BadCredentialsException("username or password invalid"));
        if (!isLoginCorrect(loginRequestDTO, bCryptPasswordEncoder, user)) {
            throw new BadCredentialsException("username or password invalid");
        }

        var now = Instant.now();
        var expiresIn = 300L;
        var scopes = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("cosmosgestaobackend")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponseDTO(jwtValue, expiresIn);
    }

    @Override
    public boolean isLoginCorrect(LoginRequestDTO loginRequestDTO,
                                  PasswordEncoder passwordEncoder, User
                                          user) {
        return passwordEncoder.matches(
                loginRequestDTO.password(),
                user.getPassword()
        );
    }

    @Override
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
