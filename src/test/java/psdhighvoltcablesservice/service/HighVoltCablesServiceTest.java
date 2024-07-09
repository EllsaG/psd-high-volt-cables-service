package psdhighvoltcablesservice.service;

import com.project.psdhighvoltcablesservice.calculation.HighVoltCalculation;
import com.project.psdhighvoltcablesservice.entity.HighVoltCablesSelection;
import com.project.psdhighvoltcablesservice.entity.HighVoltInformation;
import com.project.psdhighvoltcablesservice.repository.HighVoltCablesRepository;
import com.project.psdhighvoltcablesservice.repository.HighVoltCablesSelectionRepository;
import com.project.psdhighvoltcablesservice.repository.HighVoltInformationRepository;
import com.project.psdhighvoltcablesservice.rest.PowerTransformerByIdResponseDTO;
import com.project.psdhighvoltcablesservice.rest.PowerTransformers;
import com.project.psdhighvoltcablesservice.rest.PowerTransformersServiceClient;
import com.project.psdhighvoltcablesservice.service.HighVoltCablesService;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HighVoltCablesServiceTest {

    @Mock
    private HighVoltCablesSelectionRepository highVoltCablesSelectionRepository;
    @Mock
    private HighVoltInformationRepository highVoltInformationRepository;
    @Mock
    private HighVoltCablesRepository highVoltCablesRepository;
    @Mock
    private PowerTransformersServiceClient powerTransformersServiceClient;
    @InjectMocks
    private HighVoltCablesService highVoltCablesService;




    @Test
    public void highVoltCablesService_createHighVoltCablesSelectionInformationResponse(){

        HighVoltInformation highVoltInformation = new HighVoltInformation();
        highVoltInformation.setHighVoltInformationId((short)3);
        highVoltInformation.setRatedPowerTransformerCurrent(23.09F);
        highVoltInformation.setShortCircuitCurrentAtPointK1(8.78F);

        HighVoltCalculation highVoltCalculation = new HighVoltCalculation();
        HighVoltCablesSelection highVoltCablesSelection = highVoltCalculation
                .createHighVoltCablesSelectionInformationResponse(highVoltInformation);

        Assertions.assertEquals(73.04F, highVoltCablesSelection.getMinCableSectionForSelect());

    }


    @Test
    public void highVoltCablesService_highVoltInformationCalculation(){

        Mockito.when(powerTransformersServiceClient.getPowerTransformersInformationById(ArgumentMatchers.anyShort()))
                .thenReturn(createPowerTransformerByIdResponseDTO());
        List<Float> inductiveResistanceList = new ArrayList<>();
        inductiveResistanceList.add(0.5F);
        inductiveResistanceList.add(0.07F);
        inductiveResistanceList.add(0.2F);
        inductiveResistanceList.add(0.54F);

        HighVoltCalculation highVoltCalculation = new HighVoltCalculation();
        HighVoltInformation highVoltInformation = highVoltCalculation.highVoltInformationCalculation((short) 3, (short) 115, (short) 300,
                0.5F, 40F, 160F, 10.5F,
                2.5F, 10, inductiveResistanceList, powerTransformersServiceClient);



        Assertions.assertEquals(3, highVoltInformation.getHighVoltInformationId());
        Assertions.assertEquals(0.08F, highVoltInformation.getHighVoltageAirLineInductiveResistance());
        Assertions.assertEquals(0.08F, highVoltInformation.getHighVoltageCableLineInductiveResistance());
        Assertions.assertEquals(0.18F, highVoltInformation.getHighVoltageCableLineActiveResistance());
        Assertions.assertEquals(1.8F, highVoltInformation.getSurgeCoefficient());
        Assertions.assertEquals(1.2F, highVoltInformation.getEconomicCurrentDensity());
        Assertions.assertEquals(0.5F, highVoltInformation.getShortCircuitTime());
        Assertions.assertEquals(85.0F, highVoltInformation.getCoefficientTakingEmittedHeatDifference());
        Assertions.assertEquals(400.0F, highVoltInformation.getProductionHallTransformerFullPower());
        Assertions.assertEquals(115, highVoltInformation.getBaseVoltage());
        Assertions.assertEquals(300, highVoltInformation.getBaseFullPower());
        Assertions.assertEquals(0.5F, highVoltInformation.getRelativeBaselineUnrestrictedPowerResistance());
        Assertions.assertEquals(40.0F, highVoltInformation.getHighVoltageAirLineLength());
        Assertions.assertEquals(160.0F, highVoltInformation.getHeadTransformerFullPower());
        Assertions.assertEquals(10.5F, highVoltInformation.getShortCircuitVoltage());
        Assertions.assertEquals(2.5F, highVoltInformation.getCableLineLength());
        Assertions.assertEquals(10.0F, highVoltInformation.getRatedVoltageOfHigherVoltageWindingOfTransformer());
        Assertions.assertEquals(0.5F, highVoltInformation.getRelativeBasisResistance());
        Assertions.assertEquals(0.07F, highVoltInformation.getPowerLineRelativeResistance());
        Assertions.assertEquals(0.2F, highVoltInformation.getFirstTransformerRelativeReactiveResistance());
        Assertions.assertEquals(0.54F, highVoltInformation.getCableLineRelativeReactiveResistance());
        Assertions.assertEquals(41.25F, highVoltInformation.getSecondTransformerRelativeReactiveResistance());
        Assertions.assertEquals(1.31F, highVoltInformation.getReactiveResistanceAtPointK1());
        Assertions.assertEquals(16.5F, highVoltInformation.getBaseCurrentAtPointK1());
        Assertions.assertEquals(1.88F, highVoltInformation.getFullResistanceAtPointK1());
        Assertions.assertEquals(8.78F, highVoltInformation.getShortCircuitCurrentAtPointK1());
        Assertions.assertEquals(22.35F, highVoltInformation.getSurgeCurrentAtPointK1());
        Assertions.assertEquals(15.96F, highVoltInformation.getShortCircuitPowerAtPointK1());
        Assertions.assertEquals(23.09F, highVoltInformation.getRatedPowerTransformerCurrent());

    }


    private PowerTransformerByIdResponseDTO createPowerTransformerByIdResponseDTO(){
        PowerTransformerByIdResponseDTO powerTransformerByIdResponseDTO = new PowerTransformerByIdResponseDTO();
        PowerTransformers powerTransformers = new PowerTransformers((short) 3, "TMG-400",
                400.0F,0.1F,5.5F, 2000.0F,
                10.0F, 0.4F, 10.5F, 1.0F);

        powerTransformerByIdResponseDTO.setPowerTransformers(powerTransformers);

        return powerTransformerByIdResponseDTO;

    }



}
