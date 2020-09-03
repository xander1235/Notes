package com.notes.notes.pojos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUpdateUser implements Serializable {

    @NotBlank(message = "user name can not be blank")
    @JsonProperty("user_name")
    private String userName;

    @NotBlank(message = "old password can not be blank")
    @JsonProperty("old_password")
    private String oldPassword;

    @NotBlank(message = "new password can not be empty")
    @JsonProperty("new_password")
    private String newPassword;
}
