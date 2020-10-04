package com.notes.notes.pojos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResLogin implements Serializable {

    @JsonProperty("transaction_id")
    private String transactionId;
}
