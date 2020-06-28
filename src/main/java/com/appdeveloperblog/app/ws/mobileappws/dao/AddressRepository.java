package com.appdeveloperblog.app.ws.mobileappws.dao;

import com.appdeveloperblog.app.ws.mobileappws.entity.AddressEntity;
import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);

    AddressEntity findAddressEntityByAddressIdAndUserDetails(String addressId, UserEntity userDetails);
}
