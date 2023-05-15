package com.sun.Azit.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class AuthorityException extends RuntimeException{

    public AuthorityException(String action){
        super(action + "권한이 없습니다.");
    }
}
