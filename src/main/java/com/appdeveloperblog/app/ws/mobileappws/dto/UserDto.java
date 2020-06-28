package com.appdeveloperblog.app.ws.mobileappws.dto;

import com.appdeveloperblog.app.ws.mobileappws.ui.request.AddressRequestModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private static final long serialVersionUID = 6835192601898364280L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private List<AddressDto> addresses;
    private Set<String> roles;
}
