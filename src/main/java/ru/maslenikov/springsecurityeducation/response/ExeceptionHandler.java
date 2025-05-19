package ru.maslenikov.springsecurityeducation.response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class ExeceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public HttpEntity<String> handleException(RuntimeException ex){
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
