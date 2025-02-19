package br.ebr.cosmos.cosmos_gestao.adapters.outbound.entities;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class JpaRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public JpaRoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public JpaRoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Values {

        ADMIN(2),
        BASIC(1);

        long id;

        Values(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

    }

}
