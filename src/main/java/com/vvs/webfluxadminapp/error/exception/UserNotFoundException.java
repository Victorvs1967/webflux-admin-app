package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

public class UserNotFoundException extends RuntimeException {
  private final static String error = Error.USER_NOT_FOUND.getMessage();

  public UserNotFoundException() {
    super(error);
  }
}
