package com.project.psdhighvoltcablesservice.rest;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PowerTransformers {
    private short powerTransformersId;
    private String transformerModel;
    private float transformerFullPower;
    private float transformerLoadCoef;
    private float shortCircuitVoltage;
    private float transformerIdleLosses;
    private float highSideVoltage;
    private float lowSideVoltage;
    private float shortCircuitLosses;
    private float idleCurrent;
}
