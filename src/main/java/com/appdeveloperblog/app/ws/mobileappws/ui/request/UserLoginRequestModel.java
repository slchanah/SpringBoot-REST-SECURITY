package com.appdeveloperblog.app.ws.mobileappws.ui.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequestModel {
    private String email;
    private String password;
}
