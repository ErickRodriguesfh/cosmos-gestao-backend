package br.ebr.cosmos.cosmos_gestao.adapters.outbound.repository;

import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ebr.cosmos.cosmos_gestao.adapters.outbound.entities.JpaUserEntity;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<JpaUserEntity, Long>{

    Optional<JpaUserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

}
