package com.EMS.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.JwtException;

//sample SpringController 
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

  //sample handler
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(JwtException.class)
  public @ResponseBody String handleSQLException(HttpServletRequest request,
          Exception ex){
      String response = "some exception thrown when executing the request"; 
      return response;
  }
  //other handlers
}