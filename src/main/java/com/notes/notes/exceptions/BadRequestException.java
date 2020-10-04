package com.notes.notes.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends NotesException {

    public BadRequestException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }


}
