package br.ebr.cosmos.cosmos_gestao.domain.role;

public class Role {

    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
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
