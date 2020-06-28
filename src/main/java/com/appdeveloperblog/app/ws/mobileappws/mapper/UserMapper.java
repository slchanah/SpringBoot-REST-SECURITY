package com.appdeveloperblog.app.ws.mobileappws.mapper;

import com.appdeveloperblog.app.ws.mobileappws.dto.AddressDto;
import com.appdeveloperblog.app.ws.mobileappws.dto.UserDto;
import com.appdeveloperblog.app.ws.mobileappws.entity.AddressEntity;
import com.appdeveloperblog.app.ws.mobileappws.entity.UserEntity;
import com.appdeveloperblog.app.ws.mobileappws.ui.request.AddressRequestModel;
import com.appdeveloperblog.app.ws.mobileappws.ui.request.UserDetailsRequestModel;
import com.appdeveloperblog.app.ws.mobileappws.ui.response.UserRest;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto reqToDto(UserDetailsRequestModel userDetailsRequestModel);

    UserRest dtoToRes(UserDto userDto);

    UserEntity dtoToEntity(UserDto userDto);

    UserDto entityToDto(UserEntity userEntity);

    AddressDto addressReqToDto(AddressRequestModel addressRequestModel);

    AddressRequestModel addressDtoToReq(AddressDto addressDto);

    AddressEntity addressDtoToEntity(AddressDto addressDto);

    AddressDto addressEntityToDto(AddressEntity addressEntity);
}
