package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

public class WrongCredentialException extends RuntimeException {
  private static final Error error = Error.WRONG_CREDENTIALS;

  public WrongCredentialException() {
    super(error.getMessage());
  }  
}
