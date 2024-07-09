package com.project.psdhighvoltcablesservice.controller.dto;

import com.project.psdhighvoltcablesservice.entity.HighVoltCablesSelection;
import com.project.psdhighvoltcablesservice.entity.HighVoltInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighVoltInformationResponseDTO {
    List<HighVoltInformation> highVoltInformationList;
    List<HighVoltCablesSelection> highVoltCableSelectionList;
}
