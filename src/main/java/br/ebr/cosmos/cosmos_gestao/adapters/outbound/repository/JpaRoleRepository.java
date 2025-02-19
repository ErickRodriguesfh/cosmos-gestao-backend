package br.ebr.cosmos.cosmos_gestao.adapters.outbound.repository;

import br.ebr.cosmos.cosmos_gestao.adapters.outbound.entities.JpaRoleEntity;
import br.ebr.cosmos.cosmos_gestao.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<JpaRoleEntity, Long> {

    Optional<JpaRoleEntity> findByName(String name);

}
