package com.appdeveloperblog.app.ws.mobileappws.dao;

import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUsersByFirstName() {
        List<UserEntity> users = userRepository.findUsersByFirstName("address");
        assertNotNull(users);
        assertTrue(users.size() == 3);
    }

    @Test
    void findUsersByLastName(){
        List<UserEntity> users = userRepository.findUsersByLastName("Chan");
        assertNotNull(users);
    }

    @Test
    void findUsersByFirstAndLastName(){
        List<Object[]> users = userRepository.findUsersByFirstAndLastName("add", "Ch");
        assertNotNull(users);

        Object[] user = users.get(0);
        System.out.println(user[0]);
        System.out.println(user[1]);
    }

    @Test
    void update(){
        userRepository.update(true, "IvanBB");
        List<UserEntity> users = userRepository.findUsersByFirstName("IvanBB");
        UserEntity user = users.get(0);
        assertEquals(user.getEmailVerificationStatus(), true);
    }

    @Test
    void testJPQL(){
        UserEntity userEntity = userRepository.findUserEntity("5kVPOO9zC6BVlTzCQGHtOEePkLb6Y8");
        assertNotNull(userEntity);
        assertEquals(userEntity.getUserId(), "5kVPOO9zC6BVlTzCQGHtOEePkLb6Y8");
    }
}