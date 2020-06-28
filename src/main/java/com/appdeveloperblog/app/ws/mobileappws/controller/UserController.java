package com.appdeveloperblog.app.ws.mobileappws.controller;

import com.appdeveloperblog.app.ws.mobileappws.dto.AddressDto;
import com.appdeveloperblog.app.ws.mobileappws.dto.UserDto;
import com.appdeveloperblog.app.ws.mobileappws.exception.UserServiceException;
import com.appdeveloperblog.app.ws.mobileappws.mapper.UserMapper;
import com.appdeveloperblog.app.ws.mobileappws.service.AddressService;
import com.appdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appdeveloperblog.app.ws.mobileappws.ui.request.UserDetailsRequestModel;
import com.appdeveloperblog.app.ws.mobileappws.ui.response.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostAuthorize("hasRole('ADMIN') or returnObject.userId == principal.userId")
    @ApiOperation(value="The Get User Details Web Service Endpoint",
            notes="User public id in URL Path")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable String id){
        UserDto userDto = userService.getUserByUserId(id);
        System.out.println("_-------------in func");
        return modelMapper.map(userDto, UserRest.class);
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) throws Exception {
        if (userDetailsRequestModel.getFirstName().isEmpty()) throw new UserServiceException("Exception");

//        UserDto userDto = userMapper.reqToDto(userDetailsRequestModel);
        UserDto userDto = modelMapper.map(userDetailsRequestModel, UserDto.class);
        userDto.setRoles(new HashSet<>(Collections.singletonList("ROLE_USER")));

        UserDto savedUser = userService.createUser(userDto);

//        return userMapper.dtoToRes(savedUser);
        return modelMapper.map(savedUser, UserRest.class);
    }

    @PutMapping("/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetailsRequestModel){
        UserDto userDto = modelMapper.map(userDetailsRequestModel, UserDto.class);

        UserDto returnUser = userService.updateUser(id, userDto);

        return modelMapper.map(returnUser, UserRest.class);
    }

//    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.userId")
    @DeleteMapping("/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel operationStatusModel = OperationStatusModel.builder().operationName(OperationName.DELETE.name()).build();

        userService.deleteUser(id);

        operationStatusModel.setOperationStatus(OperationStatus.SUCCESS.name());
        return operationStatusModel;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorization.description}", paramType = "header")
    })
    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit){
        List<UserDto> userDtos = userService.getUsers(page, limit);

//        List<UserRest> userRests = new ArrayList<>();
//        for(UserDto userDto: userDtos){
//            userRests.add(userMapper.dtoToRes(userDto));
//        }
        Type listType = new TypeToken<List<UserRest>>() {}.getType();
        return modelMapper.map(userDtos, listType);
    }

    @GetMapping("/{id}/addresses")
    public CollectionModel<AddressRest> getAddresses(@PathVariable String id){
        List<AddressRest> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        List<AddressDto> addressDtoList = addressService.getAddressesByUserId(id);
        if (!addressDtoList.isEmpty()){
//            for (AddressDto addressDto: addressDtoList){
//                returnValue.add(modelMapper.map(addressDto, AddressRest.class));
//            }
            Type listType = new TypeToken<List<AddressRest>>() {}.getType();
            returnValue = modelMapper.map(addressDtoList, listType);
            for (AddressRest addressRest: returnValue){
                Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAddress(id, addressRest.getAddressId()))
                        .withSelfRel();
                addressRest.add(selfLink);
            }
        }

        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAddresses(id))
                .withSelfRel();
//
        return CollectionModel.of(returnValue, userLink, selfLink);
    }

    @GetMapping("/{id}/addresses/{addressId}")
    public EntityModel<AddressRest> getAddress(@PathVariable String id, @PathVariable String addressId){
        AddressDto addressDto = addressService.getAddress(id, addressId);
        AddressRest addressRest = modelMapper.map(addressDto, AddressRest.class);

        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");

        Link userAddressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAddresses(id))
//                .slash(id)
//                .slash("addresses")
                .withRel("addresses");
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAddress(id, addressId))
//                .slash(id)
//                .slash("addresses")
//                .slash(addressId)
                .withSelfRel();

//        addressRest.add(userLink, userAddressesLink, selfLink);
        return EntityModel.of(addressRest, Arrays.asList(userLink, userAddressesLink, selfLink));
    }

}
