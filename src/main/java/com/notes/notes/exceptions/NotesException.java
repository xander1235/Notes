package com.notes.notes.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class NotesException extends RuntimeException {

    public NotesException(String msg) {
        super(msg);
    }

    public NotesException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public abstract HttpStatus getHttpStatus();
}
