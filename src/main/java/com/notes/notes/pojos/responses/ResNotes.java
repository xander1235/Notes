package com.notes.notes.pojos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notes.notes.models.Notes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResNotes implements Serializable {

    @JsonProperty("res_notes")
    List<Notes> notes;
}
