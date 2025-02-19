package br.ebr.cosmos.cosmos_gestao.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
