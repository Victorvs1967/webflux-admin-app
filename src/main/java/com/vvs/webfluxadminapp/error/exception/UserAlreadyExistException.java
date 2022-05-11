package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

public class UserAlreadyExistException extends RuntimeException {
  private static final Error error = Error.USER_ALREADY_EXIST;

  public UserAlreadyExistException() {
    super(error.getMessage());
  }

}
