package com.example.FileManagementApplication.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServiceAdvice extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatusCode statusCode, final WebRequest request) {
    final String errorMessage = ex.getRequestPartName() + " part is missing";
    return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }
}
