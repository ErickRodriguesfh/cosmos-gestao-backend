package br.ebr.cosmos.cosmos_gestao.adapters.outbound.repository.impl;

import br.ebr.cosmos.cosmos_gestao.adapters.outbound.repository.JpaRoleRepository;
import br.ebr.cosmos.cosmos_gestao.domain.role.Role;
import br.ebr.cosmos.cosmos_gestao.domain.role.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final JpaRoleRepository repository;

    public RoleRepositoryImpl(JpaRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name.toLowerCase())
                .map(jpaRoleEntity -> {
                    Role role = new Role();
                    role.setId(jpaRoleEntity.getId());
                    role.setName(jpaRoleEntity.getName());
                    return role;
                });
    }

}
