package com.deavensoft.timetracker.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TimeTrackerException extends RuntimeException {
  private String message;
  private String details;
  private String hint;
  private String status;

  private TimeTrackerException(String message, String details, String hint, String status) {
    this.message = message;
    this.details = details;
    this.hint = hint;
    this.status = status;
  }

  private TimeTrackerException(String message, String message1, String details, String hint,
      String status) {
    super(message);
    this.message = message1;
    this.details = details;
    this.hint = hint;
    this.status = status;
  }

  private TimeTrackerException(String message, Throwable cause, String message1,
      String details, String hint, String status) {
    super(message, cause);
    this.message = message1;
    this.details = details;
    this.hint = hint;
    this.status = status;
  }

  private TimeTrackerException(Throwable cause, String message, String details, String hint,
      String status) {
    super(cause);
    this.message = message;
    this.details = details;
    this.hint = hint;
    this.status = status;
  }

  private TimeTrackerException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace, String message1, String details, String hint,
      String status) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.message = message1;
    this.details = details;
    this.hint = hint;
    this.status = status;
  }

  public static TimeTrackerException postMessageBodyNotCorrect (String message) {
    return new TimeTrackerException("User must have role(s)",
        ErrorType.BODY_NOT_CORRECT.getDescription(),
        ErrorType.CHECK_BODY.getDescription(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }
}
