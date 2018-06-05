package cz.muni.fi.pa165.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity for storing user roles.
 * Not implemented in DAO. Just for auto generating DDL and creating tables.
 * 
 * @author mato
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(name = "uni_users_role", columnNames = { "users_username", "role" }))
public class UserRoles implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    private Users users;
    
    @Column(length = 45, nullable = false)
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public RoleTypes getRoleType() {
//        return roleType;
//    }
//
//    public void setRoleType(RoleTypes roleType) {
//        this.roleType = roleType;
//    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserRoles other = (UserRoles) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserRoles{" + "id=" + id + ", users=" + users + ", role=" + role + '}';
    }
    
}
