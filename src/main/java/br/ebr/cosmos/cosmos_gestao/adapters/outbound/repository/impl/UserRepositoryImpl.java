package br.ebr.cosmos.cosmos_gestao.adapters.outbound.repository.impl;

import br.ebr.cosmos.cosmos_gestao.adapters.outbound.entities.JpaRoleEntity;
import br.ebr.cosmos.cosmos_gestao.adapters.outbound.entities.JpaUserEntity;
import br.ebr.cosmos.cosmos_gestao.adapters.outbound.repository.JpaRoleRepository;
import br.ebr.cosmos.cosmos_gestao.adapters.outbound.repository.JpaUserRepository;
import br.ebr.cosmos.cosmos_gestao.application.usecases.RoleUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.role.Role;
import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import br.ebr.cosmos.cosmos_gestao.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaRoleRepository jpaRoleRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, JpaRoleRepository jpaRoleRepository) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaRoleRepository = jpaRoleRepository;
    }

    @Override
    public User save(User user) {
        JpaUserEntity jpaUserEntity = new JpaUserEntity(user);

        Set<JpaRoleEntity> managedRoles = user.getRoles()
                .stream()
                .map(r -> jpaRoleRepository.findById(r.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + r.getId())))
                .collect(Collectors.toSet());
        jpaUserEntity.setRoles(managedRoles);

        this.jpaUserRepository.save(jpaUserEntity);
        return new User(jpaUserEntity.getId(), jpaUserEntity.getName(), jpaUserEntity.getEmail(),
                jpaUserEntity.getSector(), jpaUserEntity.getUsername(), jpaUserEntity.getPassword());
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<JpaUserEntity> jpaUserEntity = this.jpaUserRepository.findById(id);
        return jpaUserEntity.map(userEntity -> new User(userEntity.getId(), userEntity.getName(),
                userEntity.getEmail(), userEntity.getSector(), userEntity.getUsername(),
                userEntity.getPassword()));
    }

    @Override
    public List<User> findAll() {
        return this.jpaUserRepository.findAll().stream()
                .map(entity -> new User(entity.getId(), entity.getName(), entity.getEmail(), entity.getSector(),
                        entity.getUsername(), entity.getPassword()))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.jpaUserRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<JpaUserEntity> entity = jpaUserRepository.findByUsername(username);

        if (entity.isEmpty()) {
            return Optional.empty();
        }

        Set<Role> roles = entity.map(userEntity -> userEntity.getRoles().stream()
                        .map(roleEntity -> new Role(roleEntity.getId(), roleEntity.getName()))
                        .collect(Collectors.toSet()))
                .orElse(Set.of());

        return entity.map(jpaUserEntity -> new User(jpaUserEntity.getId(), jpaUserEntity.getName(), jpaUserEntity.getEmail(),
                        jpaUserEntity.getSector(), jpaUserEntity.getUsername(), jpaUserEntity.getPassword(), roles));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

}
