package com.notes.notes.pojos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUser implements Serializable {

    @NotBlank(message = "user name can not be blank")
    @JsonProperty("user_name")
    private String userName;

    @NotBlank(message = "password can not be blank")
    @JsonProperty("password")
    private String password;
}
