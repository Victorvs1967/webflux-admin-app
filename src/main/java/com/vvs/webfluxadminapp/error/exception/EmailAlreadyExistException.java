package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

import lombok.Data;

@Data
public class EmailAlreadyExistException extends RuntimeException {
  private final String email;
  private static final Error error = Error.EMAIL_ALREADY_EXIST;

  public EmailAlreadyExistException(String email) {
    super(error.getMessage());
    this.email = email;
  }
  
}
