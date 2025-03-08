package br.ebr.cosmos.cosmos_gestao.application.service;

import br.ebr.cosmos.cosmos_gestao.application.usecases.RoleUseCases;
import br.ebr.cosmos.cosmos_gestao.application.usecases.UserUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.role.Role;
import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import br.ebr.cosmos.cosmos_gestao.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserUseCases {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
