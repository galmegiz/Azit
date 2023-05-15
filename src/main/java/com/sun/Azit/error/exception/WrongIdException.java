package com.sun.Azit.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "게시물에 대한 정상적인 접근이 아닙니다.")
public class WrongIdException extends RuntimeException{

    public WrongIdException(String message){
        super(message);
    }

}
