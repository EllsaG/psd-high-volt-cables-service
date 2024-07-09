package com.project.psdhighvoltcablesservice.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "high_volt_information")
public class HighVoltInformation {

    @Id
    @Column(name = "high_volt_information_id")
    private short highVoltInformationId;
    @Column(name = "high_voltage_air_line_inductive_resistance")
    private float highVoltageAirLineInductiveResistance;
    @Column(name = "high_voltage_cable_line_inductive_resistance")
    private float highVoltageCableLineInductiveResistance;
    @Column(name = "high_voltage_cable_line_active_resistance")
    private float highVoltageCableLineActiveResistance;
    @Column(name = "surge_coefficient")
    private float surgeCoefficient;
    @Column(name = "economic_current_density")
    private float economicCurrentDensity;
    @Column(name = "short_circuit_time")
    private float shortCircuitTime;
    @Column(name = "coefficient_taking_emitted_heat_difference")
    private float coefficientTakingEmittedHeatDifference ;
    @Column(name = "production_hall_transformer_full_power")
    private float productionHallTransformerFullPower;
    @Column(name = "base_voltage")
    private short baseVoltage;
    @Column(name = "base_full_power")
    private short baseFullPower;
    @Column(name = "relative_baseline_unrestricted_power_resistance")
    private float relativeBaselineUnrestrictedPowerResistance;
    @Column(name = "high_voltage_air_line_length")
    private float highVoltageAirLineLength;
    @Column(name = "head_transformer_full_power")
    private float headTransformerFullPower;
    @Column(name = "short_circuit_voltage")
    private float shortCircuitVoltage;
    @Column(name = "cable_line_length")
    private float cableLineLength;
    @Column(name = "rated_voltage_of_higher_voltage_winding_of_transformer")
    private float ratedVoltageOfHigherVoltageWindingOfTransformer;
    @Column(name = "relative_basis_resistance")
    private float relativeBasisResistance;
    @Column(name = "power_line_relative_resistance")
    private float powerLineRelativeResistance;
    @Column(name = "first_transformer_relative_reactive_resistance")
    private float firstTransformerRelativeReactiveResistance;
    @Column(name = "cable_line_relative_reactive_resistance")
    private float cableLineRelativeReactiveResistance;
    @Column(name = "second_transformer_relative_reactive_resistance")
    private float secondTransformerRelativeReactiveResistance;
    @Column(name = "reactive_resistance_at_point_k_1")
    private float reactiveResistanceAtPointK1;
    @Column(name = "base_current_at_point_k_1")
    private float baseCurrentAtPointK1;
    @Column(name = "full_resistance_at_point_k_1")
    private float fullResistanceAtPointK1;
    @Column(name = "short_circuit_current_at_point_k_1")
    private float shortCircuitCurrentAtPointK1;
    @Column(name = "surge_current_at_point_k_1")
    private float surgeCurrentAtPointK1;
    @Column(name = "short_circuit_power_at_point_k_1")
    private float shortCircuitPowerAtPointK1;
    @Column(name = "rated_power_transformer_current")
    private float ratedPowerTransformerCurrent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighVoltInformation that = (HighVoltInformation) o;
        return highVoltInformationId == that.highVoltInformationId && Float.compare(highVoltageAirLineInductiveResistance, that.highVoltageAirLineInductiveResistance) == 0 && Float.compare(highVoltageCableLineInductiveResistance, that.highVoltageCableLineInductiveResistance) == 0 && Float.compare(highVoltageCableLineActiveResistance, that.highVoltageCableLineActiveResistance) == 0 && Float.compare(surgeCoefficient, that.surgeCoefficient) == 0 && Float.compare(economicCurrentDensity, that.economicCurrentDensity) == 0 && Float.compare(shortCircuitTime, that.shortCircuitTime) == 0 && Float.compare(coefficientTakingEmittedHeatDifference, that.coefficientTakingEmittedHeatDifference) == 0 && Float.compare(productionHallTransformerFullPower, that.productionHallTransformerFullPower) == 0 && baseVoltage == that.baseVoltage && baseFullPower == that.baseFullPower && Float.compare(relativeBaselineUnrestrictedPowerResistance, that.relativeBaselineUnrestrictedPowerResistance) == 0 && Float.compare(highVoltageAirLineLength, that.highVoltageAirLineLength) == 0 && Float.compare(headTransformerFullPower, that.headTransformerFullPower) == 0 && Float.compare(shortCircuitVoltage, that.shortCircuitVoltage) == 0 && Float.compare(cableLineLength, that.cableLineLength) == 0 && Float.compare(ratedVoltageOfHigherVoltageWindingOfTransformer, that.ratedVoltageOfHigherVoltageWindingOfTransformer) == 0 && Float.compare(relativeBasisResistance, that.relativeBasisResistance) == 0 && Float.compare(powerLineRelativeResistance, that.powerLineRelativeResistance) == 0 && Float.compare(firstTransformerRelativeReactiveResistance, that.firstTransformerRelativeReactiveResistance) == 0 && Float.compare(cableLineRelativeReactiveResistance, that.cableLineRelativeReactiveResistance) == 0 && Float.compare(secondTransformerRelativeReactiveResistance, that.secondTransformerRelativeReactiveResistance) == 0 && Float.compare(reactiveResistanceAtPointK1, that.reactiveResistanceAtPointK1) == 0 && Float.compare(baseCurrentAtPointK1, that.baseCurrentAtPointK1) == 0 && Float.compare(fullResistanceAtPointK1, that.fullResistanceAtPointK1) == 0 && Float.compare(shortCircuitCurrentAtPointK1, that.shortCircuitCurrentAtPointK1) == 0 && Float.compare(surgeCurrentAtPointK1, that.surgeCurrentAtPointK1) == 0 && Float.compare(shortCircuitPowerAtPointK1, that.shortCircuitPowerAtPointK1) == 0 && Float.compare(ratedPowerTransformerCurrent, that.ratedPowerTransformerCurrent) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(highVoltInformationId, highVoltageAirLineInductiveResistance, highVoltageCableLineInductiveResistance, highVoltageCableLineActiveResistance, surgeCoefficient, economicCurrentDensity, shortCircuitTime, coefficientTakingEmittedHeatDifference, productionHallTransformerFullPower, baseVoltage, baseFullPower, relativeBaselineUnrestrictedPowerResistance, highVoltageAirLineLength, headTransformerFullPower, shortCircuitVoltage, cableLineLength, ratedVoltageOfHigherVoltageWindingOfTransformer, relativeBasisResistance, powerLineRelativeResistance, firstTransformerRelativeReactiveResistance, cableLineRelativeReactiveResistance, secondTransformerRelativeReactiveResistance, reactiveResistanceAtPointK1, baseCurrentAtPointK1, fullResistanceAtPointK1, shortCircuitCurrentAtPointK1, surgeCurrentAtPointK1, shortCircuitPowerAtPointK1, ratedPowerTransformerCurrent);
    }
}

