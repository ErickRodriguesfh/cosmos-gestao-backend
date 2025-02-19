package br.ebr.cosmos.cosmos_gestao.application.usecases;

import br.ebr.cosmos.cosmos_gestao.domain.role.Role;

import java.util.Optional;

public interface RoleUseCases {

    Role findByName(String name);

    Optional<Role> findById(Long id);

}
