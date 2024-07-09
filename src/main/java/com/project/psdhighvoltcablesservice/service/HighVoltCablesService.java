package com.project.psdhighvoltcablesservice.service;


import com.project.psdhighvoltcablesservice.calculation.HighVoltCalculation;
import com.project.psdhighvoltcablesservice.controller.dto.HighVoltCablesResponseDTO;
import com.project.psdhighvoltcablesservice.controller.dto.HighVoltCablesSelectionInformationResponseDTO;
import com.project.psdhighvoltcablesservice.controller.dto.HighVoltInformationResponseDTO;
import com.project.psdhighvoltcablesservice.entity.*;
import com.project.psdhighvoltcablesservice.repository.*;
import com.project.psdhighvoltcablesservice.rest.PowerTransformersServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HighVoltCablesService {

    private final HighVoltCablesSelectionRepository highVoltCablesSelectionRepository;
    private final HighVoltInformationRepository highVoltInformationRepository;
    private final HighVoltCablesRepository highVoltCablesRepository;
    private final HighVoltInformationInductiveImpedanceAreasRepository highVoltInformationInductiveImpedanceAreasRepository;
    private final InductiveImpedanceAreasRepository inductiveImpedanceAreasRepository;
    private final PowerTransformersServiceClient powerTransformersServiceClient;

    @Autowired
    public HighVoltCablesService(HighVoltCablesSelectionRepository highVoltCablesSelectionRepository,
                                 HighVoltInformationRepository highVoltInformationRepository,
                                 HighVoltCablesRepository highVoltCablesRepository,
                                 HighVoltInformationInductiveImpedanceAreasRepository highVoltInformationInductiveImpedanceAreasRepository,
                                 InductiveImpedanceAreasRepository inductiveImpedanceAreasRepository,
                                 PowerTransformersServiceClient powerTransformersServiceClient) {
        this.highVoltCablesSelectionRepository = highVoltCablesSelectionRepository;
        this.highVoltInformationRepository = highVoltInformationRepository;
        this.highVoltCablesRepository = highVoltCablesRepository;
        this.highVoltInformationInductiveImpedanceAreasRepository = highVoltInformationInductiveImpedanceAreasRepository;
        this.inductiveImpedanceAreasRepository = inductiveImpedanceAreasRepository;
        this.powerTransformersServiceClient = powerTransformersServiceClient;
    }





    public HighVoltCablesSelectionInformationResponseDTO saveHighVoltCablesSelectionInformation(short highVoltInformationId, short baseVoltage, short baseFullPower,
                                                                                                float relativeBaselineUnrestrictedPowerResistance,
                                                                                                float highVoltageAirLineLength, float headTransformerFullPower,
                                                                                                float shortCircuitVoltage, float cableLineLength,
                                                                                                float ratedVoltageOfHigherVoltageWindingOfTransformer,
                                                                                                List<Float> inductiveResistanceList) {

        HighVoltCalculation highVoltCalculation = new HighVoltCalculation();
        HighVoltInformation highVoltInformation = highVoltCalculation.highVoltInformationCalculation(highVoltInformationId, baseVoltage, baseFullPower,
                relativeBaselineUnrestrictedPowerResistance, highVoltageAirLineLength, headTransformerFullPower, shortCircuitVoltage,
                cableLineLength, ratedVoltageOfHigherVoltageWindingOfTransformer, inductiveResistanceList, powerTransformersServiceClient);

        HighVoltCablesSelection highVoltCableSelection = highVoltCalculation.createHighVoltCablesSelectionInformationResponse(highVoltInformation);

        List<InductiveImpedanceAreas> inductiveImpedanceAreasList = new ArrayList<>();
        inductiveImpedanceAreasList.add(new InductiveImpedanceAreas(inductiveResistanceList.get(0)));
        inductiveImpedanceAreasList.add(new InductiveImpedanceAreas(inductiveResistanceList.get(1)));
        inductiveImpedanceAreasList.add(new InductiveImpedanceAreas(inductiveResistanceList.get(2)));
        inductiveImpedanceAreasList.add(new InductiveImpedanceAreas(inductiveResistanceList.get(3)));

        List<InductiveImpedanceAreas> inductiveImpedanceAreas = inductiveImpedanceAreasRepository.saveAll(inductiveImpedanceAreasList);

        List<HighVoltInformationInductiveImpedanceAreas> highVoltInformationInductiveImpedanceAreas = new ArrayList<>();
        highVoltInformationInductiveImpedanceAreas
                .add(new HighVoltInformationInductiveImpedanceAreas(highVoltInformationId, inductiveImpedanceAreas.get(0).getInductiveImpedanceAreasId()));
        highVoltInformationInductiveImpedanceAreas
                .add(new HighVoltInformationInductiveImpedanceAreas(highVoltInformationId, inductiveImpedanceAreas.get(1).getInductiveImpedanceAreasId()));
        highVoltInformationInductiveImpedanceAreas
                .add(new HighVoltInformationInductiveImpedanceAreas(highVoltInformationId, inductiveImpedanceAreas.get(2).getInductiveImpedanceAreasId()));
        highVoltInformationInductiveImpedanceAreas
                .add(new HighVoltInformationInductiveImpedanceAreas(highVoltInformationId, inductiveImpedanceAreas.get(3).getInductiveImpedanceAreasId()));


        highVoltInformationInductiveImpedanceAreasRepository.saveAll(highVoltInformationInductiveImpedanceAreas);
        highVoltCablesSelectionRepository.save(highVoltCableSelection);
        highVoltInformationRepository.save(highVoltInformation);

        return new HighVoltCablesSelectionInformationResponseDTO(getAllHighVoltCableSelectionInformation());

    }

    public HighVoltCablesResponseDTO saveNewHighVoltCable(short id, String cableType) {
        HighVoltCalculation highVoltCalculation = new HighVoltCalculation();
        HighVoltCables newHighVoltCable = highVoltCalculation.createNewHighVoltCable(id, cableType, highVoltInformationRepository);
        highVoltCablesRepository.save(newHighVoltCable);
        return new HighVoltCablesResponseDTO(getAllHighVoltCables());
    }


    public HighVoltCablesSelectionInformationResponseDTO updateHighVoltCableSelectionInformation(short highVoltInformationId, short baseVoltage, short baseFullPower,
                                                                                                 float relativeBaselineUnrestrictedPowerResistance,
                                                                                                 float highVoltageAirLineLength, float headTransformerFullPower, float shortCircuitVoltage,
                                                                                                 float cableLineLength, float ratedVoltageOfHigherVoltageWindingOfTransformer,
                                                                                                 List<Float> inductiveResistanceList) {

        deleteHighVoltSelectionInformationById(highVoltInformationId);

        return saveHighVoltCablesSelectionInformation(highVoltInformationId, baseVoltage, baseFullPower,
                relativeBaselineUnrestrictedPowerResistance, highVoltageAirLineLength, headTransformerFullPower, shortCircuitVoltage,
                cableLineLength, ratedVoltageOfHigherVoltageWindingOfTransformer, inductiveResistanceList);

    }

    public HighVoltCablesResponseDTO updateHighVoltCable(short highVoltInformationId, String cableType) {
        deleteHighVoltCableById(highVoltInformationId);
        return saveNewHighVoltCable(highVoltInformationId, cableType);
    }

    public HighVoltCablesResponseDTO deleteHighVoltCableById(short highVoltInformationId) {
        if (highVoltCablesRepository.existsById(highVoltInformationId)) {
            highVoltCablesRepository.deleteById(highVoltInformationId);
        }

        return new HighVoltCablesResponseDTO(getAllHighVoltCables());
    }

    public HighVoltInformationResponseDTO deleteHighVoltSelectionInformationById(short highVoltInformationId) {
        List<HighVoltInformationInductiveImpedanceAreas> highVoltInformationInductiveImpedanceAreas = new ArrayList<>();

        if (highVoltInformationInductiveImpedanceAreasRepository.existsByHighVoltInformationIdFk(highVoltInformationId)) {
            highVoltInformationInductiveImpedanceAreas.addAll(highVoltInformationInductiveImpedanceAreasRepository.findByHighVoltInformationIdFk(highVoltInformationId));
            highVoltInformationInductiveImpedanceAreasRepository.deleteAllByHighVoltInformationIdFk(highVoltInformationId);
        }

        List<Short> inductiveImpedanceAreasIds = new ArrayList<>();
        for(HighVoltInformationInductiveImpedanceAreas s:highVoltInformationInductiveImpedanceAreas){
            inductiveImpedanceAreasIds.add(s.getInductiveImpedanceAreasIdFk());
        }
        inductiveImpedanceAreasRepository.deleteAllByInductiveImpedanceAreasIdIn(inductiveImpedanceAreasIds);

        if (highVoltInformationRepository.existsById(highVoltInformationId)) {
            highVoltInformationRepository.deleteById(highVoltInformationId);
        }
        if (highVoltCablesSelectionRepository.existsById(highVoltInformationId)) {
            highVoltCablesSelectionRepository.deleteById(highVoltInformationId);
        }

        return new HighVoltInformationResponseDTO(getAllHighVoltInformation(), getAllHighVoltCableSelectionInformation());
    }


    public List<HighVoltInformation> getAllHighVoltInformation() {
        return highVoltInformationRepository.findAll();
    }

    public List<HighVoltCablesSelection> getAllHighVoltCableSelectionInformation() {
        return highVoltCablesSelectionRepository.findAll();
    }

    public List<HighVoltCables> getAllHighVoltCables() {
        return highVoltCablesRepository.findAll();
    }

}
