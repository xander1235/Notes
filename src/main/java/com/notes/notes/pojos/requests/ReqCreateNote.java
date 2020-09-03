package com.notes.notes.pojos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqCreateNote implements Serializable {

    @NotBlank(message = "title can not be blank")
    private String title;

    private String description;
}
