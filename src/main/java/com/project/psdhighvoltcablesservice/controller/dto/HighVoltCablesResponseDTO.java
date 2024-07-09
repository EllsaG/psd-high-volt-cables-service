package com.project.psdhighvoltcablesservice.controller.dto;

import com.project.psdhighvoltcablesservice.entity.HighVoltCables;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighVoltCablesResponseDTO {
    List<HighVoltCables> highVoltCablesList;
}
