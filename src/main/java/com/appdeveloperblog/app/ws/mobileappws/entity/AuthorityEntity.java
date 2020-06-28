package com.appdeveloperblog.app.ws.mobileappws.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = 5525364623839860349L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<RoleEntity> roles;

    public AuthorityEntity() {
    }
}
