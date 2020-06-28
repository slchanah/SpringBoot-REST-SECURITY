package com.appdeveloperblog.app.ws.mobileappws.service.impl;

import com.appdeveloperblog.app.ws.mobileappws.dao.RolesRepository;
import com.appdeveloperblog.app.ws.mobileappws.dao.UserRepository;
import com.appdeveloperblog.app.ws.mobileappws.dto.AddressDto;
import com.appdeveloperblog.app.ws.mobileappws.dto.UserDto;
import com.appdeveloperblog.app.ws.mobileappws.entity.AddressEntity;
import com.appdeveloperblog.app.ws.mobileappws.entity.RoleEntity;
import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import com.appdeveloperblog.app.ws.mobileappws.mapper.UserMapper;
import com.appdeveloperblog.app.ws.mobileappws.security.UserPrincipal;
import com.appdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appdeveloperblog.app.ws.mobileappws.utils.Utils;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null){
            throw new RuntimeException("Record already exists");
        }

        for(AddressDto addressDto: userDto.getAddresses()){
            addressDto.setAddressId(utils.generateAddressId(30));
            addressDto.setUserDetails(userDto);
        }

//        UserEntity userEntity = userMapper.dtoToEntity(userDto);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setUserId(utils.generateUserId(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();
        for(String role: userDto.getRoles()){
            RoleEntity roleEntity = rolesRepository.findByName(role);
            roles.add(roleEntity);
        }

        userEntity.setRoles(roles);

        UserEntity savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null){
            throw new UsernameNotFoundException("No such username");
        }
//        userEntity.setAddresses(null);
//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userEntity, userDto);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) throw new UsernameNotFoundException("No such user");

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) throw new UsernameNotFoundException("No such user");

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        if (page>0) page--;

        Pageable pageable = PageRequest.of(page, limit);
        Page<UserEntity> userPage = userRepository.findAll(pageable);

        List<UserEntity> returnValue = userPage.getContent();

        List<UserDto> returnDto = new ArrayList<>();
        for (UserEntity userEntity: returnValue){
            returnDto.add(modelMapper.map(userEntity, UserDto.class));
        }
        return returnDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null){
            throw new UsernameNotFoundException(email);
        }
//        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
        return new UserPrincipal(userEntity, null);
    }

}
