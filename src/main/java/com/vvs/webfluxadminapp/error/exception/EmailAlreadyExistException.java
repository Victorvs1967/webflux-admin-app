package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

public class EmailAlreadyExistException extends RuntimeException {
  private static final Error error = Error.EMAIL_ALREADY_EXIST;

  public EmailAlreadyExistException() {
    super(error.getMessage());
  }
  
}
