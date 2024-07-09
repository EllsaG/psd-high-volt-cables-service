package com.project.psdhighvoltcablesservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InductiveResistanceAreasRequestDTO {
    @JsonProperty("inductiveResistance")
    private float inductiveResistance;
}
