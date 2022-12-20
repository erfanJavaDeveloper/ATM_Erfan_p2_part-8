package com.payeshgaran.atm_erfan_p2.exception.http;

import com.payeshgaran.atm_erfan_p2.exception.exception_maker.NotNormalTransactionException;
import com.payeshgaran.atm_erfan_p2.exception.exception_maker.NotNormal_PAYA_Exception;
import com.payeshgaran.atm_erfan_p2.exception.exception_maker.UsernameNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        ErrorMessage message = new ErrorMessage(...);
//        return message;
//    }

    @ExceptionHandler(NotNormalTransactionException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(NotNormalTransactionException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(" Not Normal Transaction Exception  ");
        return message;
    }

    @ExceptionHandler(NotNormal_PAYA_Exception.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(NotNormal_PAYA_Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(" NotNormal PAYA Exception ");
        return message;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(" not found username  ");
        return message;
    }
}