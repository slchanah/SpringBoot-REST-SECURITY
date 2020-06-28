package com.appdeveloperblog.app.ws.mobileappws.ui.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestModel {
    private String city;
    private String country;
    private String streetName;
}
