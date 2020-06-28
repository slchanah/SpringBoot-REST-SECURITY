package com.appdeveloperblog.app.ws.mobileappws.service.impl;

import com.appdeveloperblog.app.ws.mobileappws.dao.AddressRepository;
import com.appdeveloperblog.app.ws.mobileappws.dao.UserRepository;
import com.appdeveloperblog.app.ws.mobileappws.dto.AddressDto;
import com.appdeveloperblog.app.ws.mobileappws.entity.AddressEntity;
import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import com.appdeveloperblog.app.ws.mobileappws.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public List<AddressDto> getAddressesByUserId(String id) {
        List<AddressDto> returnList = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) return returnList;

        List<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity: addresses){
            returnList.add(modelMapper.map(addressEntity, AddressDto.class));
        }

        return returnList;
    }

    @Override
    public AddressDto getAddress(String id, String addressId) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) throw new UsernameNotFoundException("No such user");

        AddressEntity addressEntity = addressRepository.findAddressEntityByAddressIdAndUserDetails(addressId, userEntity);
        if (addressEntity == null){
            addressEntity = new AddressEntity();
        }

        return modelMapper.map(addressEntity, AddressDto.class);
    }
}
