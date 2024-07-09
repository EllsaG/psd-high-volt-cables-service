package com.project.psdhighvoltcablesservice.controller.dto;


import com.project.psdhighvoltcablesservice.entity.HighVoltCablesSelection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighVoltCablesSelectionInformationResponseDTO {
    List<HighVoltCablesSelection> highVoltCablesSelectionList;
}
