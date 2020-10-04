package com.notes.notes.exceptions;

import com.notes.notes.utils.NotesUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> runtimeExceptionController(BadRequestException e) {
        ErrorDetails errorDetails = new ErrorDetails(NotesUtils.getDateWithTimeAsString(new Date()), e.getMessage(), e.getHttpStatus().value());
        return new ResponseEntity<>(errorDetails, e.getHttpStatus());
    }

}
