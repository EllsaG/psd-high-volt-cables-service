package com.project.psdhighvoltcablesservice.calculation;


import com.project.psdhighvoltcablesservice.entity.HighVoltCables;
import com.project.psdhighvoltcablesservice.entity.HighVoltCablesSelection;
import com.project.psdhighvoltcablesservice.entity.HighVoltInformation;
import com.project.psdhighvoltcablesservice.exceptions.InformationNotFoundException;
import com.project.psdhighvoltcablesservice.repository.HighVoltInformationRepository;
import com.project.psdhighvoltcablesservice.rest.PowerTransformerByIdResponseDTO;
import com.project.psdhighvoltcablesservice.rest.PowerTransformersServiceClient;

import java.util.List;
import java.util.NoSuchElementException;

public class HighVoltCalculation {

    float ratedVoltageOfLowerVoltageWindingOfTransformer = 0.4F; // Rated voltage of the low voltage winding U_НН=0,4 kV;

    final float highVoltageAirLineInductiveResistance = 0.08F; // inductive resistance of the overhead high-voltage line
    final float highVoltageCableLineInductiveResistance = 0.08F; //inductive resistance of the cable high-voltage line
    final float highVoltageCableLineActiveResistance = 0.18F; //active resistance of the cable high-voltage line
    final float surgeCoefficient = 1.8F; // impact factor (in practice uses 1.8, may be 1.3 when calculating for a long high voltage line)
    final float economicCurrentDensity = 1.2F; /* А/〖мм〗^2 – economic density of the current (maybe different).
    It will be necessary to make a selection by the condition "if...else" according to the table https://www.elec.ru/library/direction/pue/razdel-1-3-6.html*/
    final float fixedTime = 0.5F; /*Set reduced short-circuit time, seconds.
    It is assumed to be equal to the actual one, which is composed of the time delay of relay protection of 10 kV lines and own time of the tripping device.*/
    final float coefficientTakingEmittedHeatDifference = 85.0F; /*A factor that takes into account the difference in the heat released
    in the conductor before and after the short circuit.
    For cables with aluminium conductors the coefficient = 85 */


    public HighVoltInformation highVoltInformationCalculation(short highVoltInformationId, short baseVoltage, short baseFullPower,
                                                              float relativeBaselineUnrestrictedPowerResistance,
                                                              float highVoltageAirLineLength, float headTransformerFullPower, float shortCircuitVoltage,
                                                              float cableLineLength, float ratedVoltageOfHigherVoltageWindingOfTransformer,
                                                              List<Float> inductiveResistanceList,
                                                              PowerTransformersServiceClient powerTransformersServiceClient) {

        PowerTransformerByIdResponseDTO powerTransformerByIdResponseDTO = powerTransformersServiceClient.getPowerTransformersInformationById(highVoltInformationId);
        if (powerTransformerByIdResponseDTO == null){
            throw new InformationNotFoundException("Unable to find information about power transformer with id № " + highVoltInformationId);
        }

        float productionHallTransformerShortCircuitVoltage = powerTransformerByIdResponseDTO.getPowerTransformers().getShortCircuitVoltage();
        float productionHallTransformerFullPower = powerTransformerByIdResponseDTO.getPowerTransformers().getTransformerFullPower();
        float relativeBasisResistance = relativeBaselineUnrestrictedPowerResistance; //relative basis resistance x_c=x_1=0,5

        float powerLineRelativeResistance = Math.round(highVoltageAirLineInductiveResistance *
                highVoltageAirLineLength * baseFullPower / Math.pow(baseVoltage, 2) *100)/100.0F;


        float firstTransformerRelativeReactiveResistance = Math.round(shortCircuitVoltage * baseFullPower / (100 * headTransformerFullPower) *100)/100.0F;


        float cableLineRelativeReactiveResistance = Math.round(highVoltageCableLineInductiveResistance * cableLineLength *
                baseFullPower / Math.pow(shortCircuitVoltage, 2) *100)/100.0F;


        float cableLineRelativeActiveResistance = Math.round(highVoltageCableLineActiveResistance * cableLineLength *
                baseFullPower / Math.pow(ratedVoltageOfHigherVoltageWindingOfTransformer, 2) *100)/100.0F;

        float secondTransformerRelativeReactiveResistance = Math.round(productionHallTransformerShortCircuitVoltage * baseFullPower /
                (100 * productionHallTransformerFullPower / 1000)*100)/100.0F;


        float reactiveResistanceAtPointK1 = Math.round(inductiveResistanceList.stream()
                .reduce(Float::sum)
                .orElseThrow(() -> new NoSuchElementException("No value present")) *100)/100.0F; //  (x_∑k1 = x_1 + ... + x_4)

        float baseCurrentAtPointK1 = Math.round(baseFullPower / (Math.sqrt(3) * shortCircuitVoltage) *100)/100.0F;

        float fullResistanceAtPointK1 = Math.round(Math.sqrt((Math.pow(cableLineRelativeActiveResistance, 2) + Math.pow(reactiveResistanceAtPointK1, 2))) *100)/100.0F;



        float shortCircuitCurrentAtPointK1 = Math.round(baseCurrentAtPointK1 / fullResistanceAtPointK1*100)/100.0F;


        float surgeCurrentAtPointK1 = Math.round(Math.sqrt(2) * surgeCoefficient * shortCircuitCurrentAtPointK1 *100)/100.0F;

        float shortCircuitPowerAtPointK1 = Math.round(baseFullPower / fullResistanceAtPointK1 *10)/100.0F;


        float ratedPowerTransformerCurrent = Math.round(productionHallTransformerFullPower /
                (ratedVoltageOfHigherVoltageWindingOfTransformer * Math.sqrt(3)) *100)/100.0F;


        return new HighVoltInformation(highVoltInformationId, highVoltageAirLineInductiveResistance, highVoltageCableLineInductiveResistance, highVoltageCableLineActiveResistance,
                surgeCoefficient, economicCurrentDensity, fixedTime, coefficientTakingEmittedHeatDifference, productionHallTransformerFullPower, baseVoltage,
                baseFullPower, relativeBaselineUnrestrictedPowerResistance, highVoltageAirLineLength, headTransformerFullPower, shortCircuitVoltage, cableLineLength,
                ratedVoltageOfHigherVoltageWindingOfTransformer, relativeBasisResistance, powerLineRelativeResistance, firstTransformerRelativeReactiveResistance,
                cableLineRelativeReactiveResistance, secondTransformerRelativeReactiveResistance, reactiveResistanceAtPointK1, baseCurrentAtPointK1,
                fullResistanceAtPointK1, shortCircuitCurrentAtPointK1, surgeCurrentAtPointK1, shortCircuitPowerAtPointK1, ratedPowerTransformerCurrent);

    }

    public HighVoltCablesSelection createHighVoltCablesSelectionInformationResponse(HighVoltInformation highVoltInformation) {
        short id = highVoltInformation.getHighVoltInformationId();
        float ratedPowerTransformerCurrent = highVoltInformation.getRatedPowerTransformerCurrent();
        float shortCircuitCurrentAtPointK1 = highVoltInformation.getShortCircuitCurrentAtPointK1();

        float cableSection = Math.round( ratedPowerTransformerCurrent / economicCurrentDensity*100)/100.0F; // Calculation of section based on economic density S, mm2


        float minCableSectionForSelection = Math.round(shortCircuitCurrentAtPointK1 * 1000 * Math.sqrt(fixedTime) /
                coefficientTakingEmittedHeatDifference*100)/100.0F; // Testing the cable for thermal resistance to short-circuit currents Smin , mm2


        if (cableSection > minCableSectionForSelection) {
            return new HighVoltCablesSelection(id, cableSection);
        } else {
            return new HighVoltCablesSelection(id, minCableSectionForSelection);
        }
    }

    public HighVoltCables createNewHighVoltCable(short highVoltInformationId, String cableType, HighVoltInformationRepository highVoltInformationRepository){

        if(highVoltInformationRepository.existsById(highVoltInformationId)){
            return new HighVoltCables(highVoltInformationId,cableType);
        }else {
            throw new InformationNotFoundException("Unable to find high volt information. Check the availability of the calculation.");
        }
    }
}
