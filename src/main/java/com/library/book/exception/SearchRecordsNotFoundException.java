package com.library.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class SearchRecordsNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -6903899576056230757L;

  public SearchRecordsNotFoundException(final String message) {
    super(message);

  }

}
