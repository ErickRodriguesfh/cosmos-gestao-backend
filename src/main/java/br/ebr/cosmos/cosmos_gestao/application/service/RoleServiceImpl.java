package br.ebr.cosmos.cosmos_gestao.application.service;

import br.ebr.cosmos.cosmos_gestao.application.usecases.RoleUseCases;
import br.ebr.cosmos.cosmos_gestao.domain.role.Role;
import br.ebr.cosmos.cosmos_gestao.domain.role.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleUseCases {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

}
