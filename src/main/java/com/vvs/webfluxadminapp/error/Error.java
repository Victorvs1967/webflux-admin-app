package com.vvs.webfluxadminapp.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Error {
  
  WRONG_CREDENTIALS(1000, "Wrong credentials"),
  UN_AUTHORIZED(1001, "User is un-authorize to access this resource"),
  USER_ALREADY_EXIST(1002, "User already exist"),
  EMAIL_ALREADY_EXIST(1003, "Email already exist"),
  USER_NOT_FOUND(1004, "User not found");

  private int code;
  private String message;

  Error(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
