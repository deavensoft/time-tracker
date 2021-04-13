package com.deavensoft.timetracker.exception;

public enum ErrorType {

  BODY_NOT_CORRECT("The object's body does not contain the corresponding data"),
  CHECK_BODY("Check body");

  private String description;

  ErrorType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
