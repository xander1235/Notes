package com.notes.notes.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetails implements Serializable {

    private String timestamp;
    private String message;
    @JsonProperty("response_code")
    private int responseCode;
}
