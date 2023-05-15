package com.sun.Azit.error.advice;

import com.sun.Azit.error.exception.WrongIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    @ResponseBody
    public String wrongIdExHandler(WrongIdException ex){
        return ex.getMessage();
    }

    //todo : admin과 일반 사용자 반환값 구분 작성 필요
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ModelAndView entityNotFoundExHandler(EntityNotFoundException ex){
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMessage", ex.getMessage());
        mv.setViewName("event/eventList");
        return mv;
    }
}
