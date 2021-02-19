package com.deavensoft.timetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(TimeTrackerException.class)
  public final ResponseEntity<Object> handleAllTimeTrackerExceptions(TimeTrackerException ex) {
    CustomExceptionResponse customExceptionResponseResponse =
        new CustomExceptionResponse(
            ex.getMessage(), ex.getDetails(), ex.getHint(), ex.getStatus());
    return new ResponseEntity<>(customExceptionResponseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public final ResponseEntity<Object> handleAllIllegalArgumentExceptions(IllegalArgumentException ex) {
    CustomExceptionResponse customExceptionResponseResponse =
        new CustomExceptionResponse(
            ex.getMessage(), ex.getLocalizedMessage(), "", HttpStatus.INTERNAL_SERVER_ERROR
            .getReasonPhrase());
    return new ResponseEntity<>(customExceptionResponseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
