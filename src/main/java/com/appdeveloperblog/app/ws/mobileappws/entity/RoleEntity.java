package com.appdeveloperblog.app.ws.mobileappws.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 5313495469894403L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
               joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    private Set<AuthorityEntity> authorities;

    public RoleEntity() {
    }
}
