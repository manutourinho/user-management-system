package habsida.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(columnDefinition = "varchar(255) default 'USER")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(Long idRole, String roleName, Set<User> users) {
        this.idRole = idRole;
        this.roleName = roleName;
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + idRole +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(idRole, role.idRole);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idRole);
    }

    //    ✿✿✿ getters & setters ✿✿✿
    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
