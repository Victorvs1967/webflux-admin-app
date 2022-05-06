package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

import lombok.Data;

@Data
public class WrongCredentialException extends RuntimeException {
  private final String username;
  private static final Error error = Error.WRONG_CREDENTIALS;

  public WrongCredentialException(String username) {
    super(error.getMessage());
    this.username = username;
  }  
}
