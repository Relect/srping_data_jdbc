package com.example.spring_data_jdbc.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex, WebRequest request) {
        String errMessage = ex.getMessage();
        var servletRequest = ((ServletWebRequest) request).getRequest();
        var urlBuild = servletRequest.getRequestURL();
        var params = servletRequest.getQueryString();
        String url = urlBuild.append(params == null ? "" : "?" + params).toString();
        ErrorMessage message = new ErrorMessage(errMessage, url);
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "произошла ошибка:" + ex.getMessage());
        return modelAndView;
    }

    private record ErrorMessage (String message, String url){

    }
}