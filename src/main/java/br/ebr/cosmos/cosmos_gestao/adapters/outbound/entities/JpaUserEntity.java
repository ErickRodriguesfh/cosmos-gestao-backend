package br.ebr.cosmos.cosmos_gestao.adapters.outbound.entities;

import br.ebr.cosmos.cosmos_gestao.domain.user.User;
import jakarta.persistence.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
public class JpaUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String sector;

    @Column(unique = true)
    private String username;

    private String password;
    private String imageUrl;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<JpaRoleEntity> roles;

    public JpaUserEntity(Long id, String name, String email, String sector, String username, String password, String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sector = sector;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public JpaUserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.sector = user.getSector();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.imageUrl = user.getImageUrl();
    }

    public JpaUserEntity() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<JpaRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<JpaRoleEntity> roles) {
        this.roles = roles;
    }

}
