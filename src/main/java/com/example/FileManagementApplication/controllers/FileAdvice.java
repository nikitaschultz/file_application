package com.example.FileManagementApplication.controllers;

import com.example.FileManagementApplication.exceptions.FileException;
import com.example.FileManagementApplication.exceptions.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FileAdvice {
  @ResponseBody
  @ExceptionHandler(FileNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  private String fileNotFoundHandler(final FileNotFoundException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(FileException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private String fileHandler(final FileException ex) {
    return ex.getMessage();
  }

}
