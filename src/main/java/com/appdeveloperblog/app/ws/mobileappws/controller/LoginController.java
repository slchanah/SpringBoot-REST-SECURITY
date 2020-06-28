package com.appdeveloperblog.app.ws.mobileappws.controller;

import com.appdeveloperblog.app.ws.mobileappws.ui.request.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @ApiOperation("User Login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response Header",
                responseHeaders = {
                    @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT value here>",
                                    response = String.class),
                    @ResponseHeader(name = "userId",
                                    description = "<PUblic User ID value here>",
                                    response = String.class)
                })
    })
    @PostMapping("/login")
    public void fakeLogin(@RequestBody LoginRequestModel loginRequestModel){
        throw new IllegalStateException("This is handled by spring security");
    }
}
