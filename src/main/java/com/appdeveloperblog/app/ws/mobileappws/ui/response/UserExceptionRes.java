package com.appdeveloperblog.app.ws.mobileappws.ui.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExceptionRes {
    private Date timestamp;
    private String message;
}
