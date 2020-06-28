package com.appdeveloperblog.app.ws.mobileappws;

import com.appdeveloperblog.app.ws.mobileappws.dao.AuthorityRepository;
import com.appdeveloperblog.app.ws.mobileappws.dao.RolesRepository;
import com.appdeveloperblog.app.ws.mobileappws.dao.UserRepository;
import com.appdeveloperblog.app.ws.mobileappws.entity.AuthorityEntity;
import com.appdeveloperblog.app.ws.mobileappws.entity.RoleEntity;
import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import com.appdeveloperblog.app.ws.mobileappws.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitialUserSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event){
        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity user = createRole("ROLE_USER", new HashSet<AuthorityEntity>(Arrays.asList(writeAuthority, readAuthority)));
        RoleEntity admin = createRole("ROLE_ADMIN", new HashSet<AuthorityEntity>(Arrays.asList(writeAuthority, readAuthority, deleteAuthority)));

        if (admin == null) return;


        UserEntity adminUser = userRepository.findByEmail("admin@email.com");
        if (adminUser == null) {
            adminUser = new UserEntity();
            adminUser.setFirstName("Ivan");
            adminUser.setLastName("Chan");
            adminUser.setEmail("admin@email.com");
            adminUser.setUserId(utils.generateUserId(30));
            adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
            adminUser.setRoles(new HashSet<>(Collections.singletonList(admin)));

            userRepository.save(adminUser);
        }
    }

    @Transactional
    private AuthorityEntity createAuthority(String name){
        AuthorityEntity authorityEntity = authorityRepository.findByName(name);
        if (authorityEntity == null){
            authorityEntity = new AuthorityEntity();
            authorityEntity.setName(name);
            authorityRepository.save(authorityEntity);
        }
        return authorityEntity;
    }

    @Transactional
    private RoleEntity createRole(String name, Set<AuthorityEntity> authorities){
        RoleEntity roleEntity = rolesRepository.findByName(name);
        if (roleEntity == null){
            roleEntity = new RoleEntity();
            roleEntity.setName(name);
            roleEntity.setAuthorities(authorities);
            rolesRepository.save(roleEntity);
        }
        return roleEntity;
    }
}
