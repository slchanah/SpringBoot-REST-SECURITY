package com.appdeveloperblog.app.ws.mobileappws.dao;

import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);

    @Query(value = "select * from users u where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUsersByFirstName(String firstName);

    @Query(value = "select * from users u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUsersByLastName(@Param("lastName") String lastName);

    @Query(value = "select u.first_name, u.last_name from users u where u.first_name like :firstName% and u.last_name like :lastName%", nativeQuery = true)
    List<Object[]> findUsersByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Transactional
    @Modifying
    @Query(value = "update users u set u.email_verification_status=:emailVerificationStatus where u.first_name = :firstName", nativeQuery = true)
    void update(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("firstName") String firstName);

    @Query(value = "select user from UserEntity user where user.userId = :userId")
    UserEntity findUserEntity(@Param("userId") String userId);
}
