package com.appdeveloperblog.app.ws.mobileappws.service;

import com.appdeveloperblog.app.ws.mobileappws.dto.AddressDto;
import com.appdeveloperblog.app.ws.mobileappws.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUser(String email);

    UserDto getUserByUserId(String id);

    UserDto updateUser(String id, UserDto userDto);

    void deleteUser(String id);

    List<UserDto> getUsers(int page, int limit);
}
