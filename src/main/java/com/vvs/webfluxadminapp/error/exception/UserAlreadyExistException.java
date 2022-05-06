package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

import lombok.Data;

@Data
public class UserAlreadyExistException extends RuntimeException {
  private final String username;
  private static final Error error = Error.USER_ALREADY_EXIST;

  public UserAlreadyExistException(String username) {
    super(error.getMessage());
    this.username = username;
  }

}
