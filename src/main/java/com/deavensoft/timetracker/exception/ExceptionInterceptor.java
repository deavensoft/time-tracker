package com.deavensoft.timetracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
  public final ResponseEntity<Object> handleAllIllegalArgumentExceptions(
      IllegalArgumentException ex) {
    CustomExceptionResponse customExceptionResponseResponse =
        new CustomExceptionResponse(
            ex.getMessage(), ex.getLocalizedMessage(), "", HttpStatus.INTERNAL_SERVER_ERROR
            .getReasonPhrase());
    return new ResponseEntity<>(customExceptionResponseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    CustomExceptionResponse customExceptionResponseResponse =
        new CustomExceptionResponse(
            ex.getMessage(), ex.getLocalizedMessage(), "", HttpStatus.BAD_REQUEST
            .getReasonPhrase());
    return new ResponseEntity<>(customExceptionResponseResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    CustomExceptionResponse customExceptionResponseResponse =
        new CustomExceptionResponse(
            ex.getMessage(), ex.getLocalizedMessage(), "", HttpStatus.BAD_REQUEST
            .getReasonPhrase());
    return new ResponseEntity<>(customExceptionResponseResponse, HttpStatus.BAD_REQUEST);
  }
}
