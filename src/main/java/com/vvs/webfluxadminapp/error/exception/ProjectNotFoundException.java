package com.vvs.webfluxadminapp.error.exception;

import com.vvs.webfluxadminapp.error.Error;

public class ProjectNotFoundException extends RuntimeException {
  private final static String error = Error.PROJECT_NOT_FOUND.getMessage();

  public ProjectNotFoundException() {
    super(error);
  }
}
