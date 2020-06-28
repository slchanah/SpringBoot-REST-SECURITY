package com.appdeveloperblog.app.ws.mobileappws.exception;

import com.appdeveloperblog.app.ws.mobileappws.ui.response.UserExceptionRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<UserExceptionRes> handleException(Exception exception, WebRequest webRequest){
        System.out.println("All Exception");
        UserExceptionRes userExceptionRes = new UserExceptionRes(new Date(), exception.getMessage());
        return new ResponseEntity<>(userExceptionRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<UserExceptionRes> handleUserServiceException(UserServiceException userServiceException, WebRequest request){
        System.out.println("Specific Exception");
        UserExceptionRes userExceptionRes = new UserExceptionRes(new Date(), userServiceException.getMessage());
        return new ResponseEntity<>(userExceptionRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
