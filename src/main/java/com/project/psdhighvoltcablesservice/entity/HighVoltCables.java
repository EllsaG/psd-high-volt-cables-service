package com.project.psdhighvoltcablesservice.entity;

import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "high_volt_cables")
public class HighVoltCables {

    @Id
    @Column(name = "high_volt_cables_id")
    private short highVoltCablesId;
    @Column(name = "cable_type")
    private String cableType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighVoltCables that = (HighVoltCables) o;
        return highVoltCablesId == that.highVoltCablesId && Objects.equals(cableType, that.cableType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(highVoltCablesId, cableType);
    }
}
