package br.ebr.cosmos.cosmos_gestao.domain.user;

import br.ebr.cosmos.cosmos_gestao.domain.role.Role;

import java.util.Set;

public class User {

    private Long id;
    private String name;
    private String email;
    private String sector;
    private String username;
    private String password;
    private String imageUrl;

    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String name, String email, String sector, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sector = sector;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String name, String email, String sector, String username, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sector = sector;
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
