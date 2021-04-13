package com.deavensoft.timetracker.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TimeTrackerException extends RuntimeException {
  private final String message;
  private final String details;
  private final String hint;
  private final String status;

  private TimeTrackerException(String message, String details, String hint, String status) {
    this.message = message;
    this.details = details;
    this.hint = hint;
    this.status = status;
  }

  public static TimeTrackerException postMessageBodyNotCorrect (String message) {
    return new TimeTrackerException(message,
        ErrorType.BODY_NOT_CORRECT.getDescription(),
        ErrorType.CHECK_BODY.getDescription(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }
}
