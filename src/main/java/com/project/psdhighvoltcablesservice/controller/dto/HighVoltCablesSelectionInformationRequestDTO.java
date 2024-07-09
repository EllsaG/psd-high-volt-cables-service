package com.project.psdhighvoltcablesservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighVoltCablesSelectionInformationRequestDTO {
    @JsonProperty("highVoltCableSelectionId")
    private short highVoltCableSelectionId;
    @JsonProperty("cableType")
    private String cableType;
}
