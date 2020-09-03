package com.notes.notes.pojos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqDeleteMultiUser implements Serializable {

    @NotEmpty(message = "title list can not be empty")
    @JsonProperty("title_list")
    private List<String> titleList;
}
