package com.deavensoft.timetracker.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomExceptionResponse {
  private String message;
  private String details;
  private String hint;
  private String status;
}
