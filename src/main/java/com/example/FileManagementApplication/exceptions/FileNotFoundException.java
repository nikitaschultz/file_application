package com.example.FileManagementApplication.exceptions;

public class FileNotFoundException extends FileException {
  public FileNotFoundException(final String message) {
    super(message);
  }

  public FileNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
