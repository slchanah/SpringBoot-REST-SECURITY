package com.appdeveloperblog.app.ws.mobileappws.service;

import com.appdeveloperblog.app.ws.mobileappws.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddressesByUserId(String id);

    AddressDto getAddress(String id, String addressId);
}
