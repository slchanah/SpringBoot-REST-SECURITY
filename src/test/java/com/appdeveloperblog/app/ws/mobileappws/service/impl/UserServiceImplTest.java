package com.appdeveloperblog.app.ws.mobileappws.service.impl;

import com.appdeveloperblog.app.ws.mobileappws.dao.UserRepository;
import com.appdeveloperblog.app.ws.mobileappws.dto.AddressDto;
import com.appdeveloperblog.app.ws.mobileappws.dto.UserDto;
import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import com.appdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appdeveloperblog.app.ws.mobileappws.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    private Utils utils;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    String userId = "dfbdfvsg";

    String encodedPassword = "b7fd87dfb8bb7";

    UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();

        userEntity.setId(1L);
        userEntity.setFirstName("Ivan");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword("vv7df7v6dfb68");
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("test");

        assertNotNull(userDto);
        assertEquals("Ivan", userDto.getFirstName());
    }

    @Test
    void getUser_UserNotFound(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.getUser("test"));
    }

    @Test
    void createUser_Error(){
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setEmail("test");
        assertThrows(RuntimeException.class, () -> userService.createUser(userDto));
    }

    @Test
    void createUser(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("fvfg4bhbu32");
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        AddressDto addressDto = AddressDto.builder().city("HK").build();
        List<AddressDto> addressDtoList = new ArrayList<>();
        addressDtoList.add(addressDto);

        UserDto userDto = UserDto.builder().addresses(addressDtoList).build();
        UserDto storedUser = userService.createUser(userDto);

        assertNotNull(storedUser);
        assertEquals(userEntity.getFirstName(), storedUser.getFirstName());
        verify(bCryptPasswordEncoder, times(1)).encode(null);
    }
}