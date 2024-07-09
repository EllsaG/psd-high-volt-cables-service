package com.project.psdhighvoltcablesservice.controller;

import com.project.psdhighvoltcablesservice.controller.dto.*;
import com.project.psdhighvoltcablesservice.service.HighVoltCablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HighVoltCablesController {

    private final HighVoltCablesService highVoltCablesService;

    @Autowired
    public HighVoltCablesController(HighVoltCablesService highVoltCablesService) {
        this.highVoltCablesService = highVoltCablesService;
    }

    @GetMapping("/selectionInformation/getAllInformation")
    public HighVoltCablesSelectionInformationResponseDTO getAllHighVoltCableSelectionInformation(){
        return new HighVoltCablesSelectionInformationResponseDTO(highVoltCablesService.getAllHighVoltCableSelectionInformation());
    }

    @GetMapping("/getAllInformation")
    public HighVoltCablesResponseDTO getAllHighVoltCables(){
        return new HighVoltCablesResponseDTO(highVoltCablesService.getAllHighVoltCables());
    }

    @PutMapping("/create/selectionInformation")
    public HighVoltCablesSelectionInformationResponseDTO createHighVoltCablesSelectionInformation(@RequestBody HighVoltInformationRequestDTO highVoltInformationRequestDTO) {
        return highVoltCablesService.saveHighVoltCablesSelectionInformation(highVoltInformationRequestDTO.getHighVoltInformationId(),
                highVoltInformationRequestDTO.getBaseVoltage(),highVoltInformationRequestDTO.getBaseFullPower(),
                highVoltInformationRequestDTO.getRelativeBaselineUnrestrictedPowerResistance(),highVoltInformationRequestDTO.getHighVoltageAirLineLength(),
                highVoltInformationRequestDTO.getHeadTransformerFullPower(),highVoltInformationRequestDTO.getShortCircuitVoltage(),
                highVoltInformationRequestDTO.getCableLineLength(),highVoltInformationRequestDTO.getRatedVoltageOfHigherVoltageWindingOfTransformer(),
                highVoltInformationRequestDTO.getInductiveResistanceList());

    }

    @PutMapping("/create/highVoltCable")
    public HighVoltCablesResponseDTO createNewHighVoltCable(@RequestBody HighVoltCablesSelectionInformationRequestDTO highVoltCablesSelectionInformationRequestDTO) {
        return highVoltCablesService.saveNewHighVoltCable(
                highVoltCablesSelectionInformationRequestDTO.getHighVoltCableSelectionId(),
                highVoltCablesSelectionInformationRequestDTO.getCableType());
    }
    @PutMapping("/update/selectionInformation")
    public HighVoltCablesSelectionInformationResponseDTO updateHighVoltCablesSelectionInformation(@RequestBody HighVoltInformationRequestDTO highVoltInformationRequestDTO) {
        return highVoltCablesService.updateHighVoltCableSelectionInformation(highVoltInformationRequestDTO.getHighVoltInformationId(),
                highVoltInformationRequestDTO.getBaseVoltage(),highVoltInformationRequestDTO.getBaseFullPower(),
                highVoltInformationRequestDTO.getRelativeBaselineUnrestrictedPowerResistance(),highVoltInformationRequestDTO.getHighVoltageAirLineLength(),
                highVoltInformationRequestDTO.getHeadTransformerFullPower(),highVoltInformationRequestDTO.getShortCircuitVoltage(),
                highVoltInformationRequestDTO.getCableLineLength(),highVoltInformationRequestDTO.getRatedVoltageOfHigherVoltageWindingOfTransformer(),
                highVoltInformationRequestDTO.getInductiveResistanceList());

    }

    @PutMapping("/update/highVoltCable")
    public HighVoltCablesResponseDTO updateHighVoltCable(@RequestBody HighVoltCablesSelectionInformationRequestDTO highVoltCablesSelectionInformationRequestDTO) {
        return highVoltCablesService.updateHighVoltCable(
                highVoltCablesSelectionInformationRequestDTO.getHighVoltCableSelectionId(),
                highVoltCablesSelectionInformationRequestDTO.getCableType());
    }

    @DeleteMapping("/delete/{highVoltInformationId}")
    public HighVoltCablesResponseDTO deleteCableById(@PathVariable short highVoltInformationId){
        return highVoltCablesService.deleteHighVoltCableById(highVoltInformationId);
    }
    @DeleteMapping("/delete/selectionInformation/{highVoltInformationId}")
    public HighVoltInformationResponseDTO deleteHighVoltSelectionInformationById(@PathVariable short highVoltInformationId){
        return highVoltCablesService.deleteHighVoltSelectionInformationById(highVoltInformationId);
    }




}
