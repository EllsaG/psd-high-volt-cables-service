package com.project.psdhighvoltcablesservice.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "high_volt_cable_selection")
public class HighVoltCablesSelection {

    @Id
    @Column(name = "high_volt_cable_selection_id")
    private short highVoltCableSelectionId;
    @Column(name = "min_cable_section_for_select")
    private float minCableSectionForSelect;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighVoltCablesSelection that = (HighVoltCablesSelection) o;
        return highVoltCableSelectionId == that.highVoltCableSelectionId && Float.compare(minCableSectionForSelect, that.minCableSectionForSelect) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(highVoltCableSelectionId, minCableSectionForSelect);
    }
}