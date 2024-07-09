package com.project.psdhighvoltcablesservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighVoltInformationRequestDTO {

    @JsonProperty("highVoltInformationId")
    private short highVoltInformationId;
    @JsonProperty("baseVoltage")
    private short baseVoltage;
    @JsonProperty("baseFullPower")
    private short baseFullPower;
    @JsonProperty("relativeBaselineUnrestrictedPowerResistance")
    private float relativeBaselineUnrestrictedPowerResistance;
    @JsonProperty("highVoltageAirLineLength")
    private float highVoltageAirLineLength;
    @JsonProperty("headTransformerFullPower")
    private float headTransformerFullPower;
    @JsonProperty("shortCircuitVoltage")
    private float shortCircuitVoltage;
    @JsonProperty("cableLineLength")
    private float cableLineLength;
    @JsonProperty("ratedVoltageOfHigherVoltageWindingOfTransformer")
    private float ratedVoltageOfHigherVoltageWindingOfTransformer;
    @JsonProperty("inductiveResistanceList")
    private List<Float> inductiveResistanceList;
}
