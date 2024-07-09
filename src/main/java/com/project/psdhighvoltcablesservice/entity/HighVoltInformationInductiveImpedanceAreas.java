package com.project.psdhighvoltcablesservice.entity;

import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "high_volt_information_inductive_impedance_areas")
public class HighVoltInformationInductiveImpedanceAreas {

    @Id
    @Column(name = "high_volt_information_inductive_impedance_areas_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short highVoltInformationInductiveImpedanceAreasId;

    @Column(name = "high_volt_information_id_fk")
    private Short highVoltInformationIdFk;

    @Column(name = "inductive_impedance_areas_id_fk")
    private Short inductiveImpedanceAreasIdFk;

    public HighVoltInformationInductiveImpedanceAreas(Short highVoltInformationIdFk, Short inductiveImpedanceAreasIdFk) {
        this.highVoltInformationIdFk = highVoltInformationIdFk;
        this.inductiveImpedanceAreasIdFk = inductiveImpedanceAreasIdFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighVoltInformationInductiveImpedanceAreas that = (HighVoltInformationInductiveImpedanceAreas) o;
        return highVoltInformationInductiveImpedanceAreasId == that.highVoltInformationInductiveImpedanceAreasId && Objects.equals(highVoltInformationIdFk, that.highVoltInformationIdFk) && Objects.equals(inductiveImpedanceAreasIdFk, that.inductiveImpedanceAreasIdFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(highVoltInformationInductiveImpedanceAreasId, highVoltInformationIdFk, inductiveImpedanceAreasIdFk);
    }
}
