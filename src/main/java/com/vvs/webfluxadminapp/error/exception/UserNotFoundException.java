package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {
  private final String username;
  private static final Error error = Error.USER_NOT_FOUND;

  public UserNotFoundException(String username) {
    super(error.getMessage());
    this.username = username;
  }
}
