package br.ebr.cosmos.cosmos_gestao.application.usecases;

import java.util.List;
import java.util.Optional;

import br.ebr.cosmos.cosmos_gestao.application.dto.LoginRequestDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.LoginResponseDTO;
import br.ebr.cosmos.cosmos_gestao.application.dto.UserDTO;
import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserUseCases {

    User save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    boolean isLoginCorrect(LoginRequestDTO loginRequestDTO,
                           PasswordEncoder passwordEncoder,
                           User user);

    void register(User user);

}
