package com.campfire.smeal.handler.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class CustomAuthenticationException extends AuthenticationException {
    private String errorMessage;

    public CustomAuthenticationException(String message){
        super(message);
        this.errorMessage = message;
    }

}
