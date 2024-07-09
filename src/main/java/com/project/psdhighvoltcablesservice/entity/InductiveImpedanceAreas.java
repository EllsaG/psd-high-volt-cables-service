package com.project.psdhighvoltcablesservice.entity;

import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "inductive_impedance_areas")
public class InductiveImpedanceAreas {
    @Id
    @Column(name = "inductive_impedance_areas_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private short inductiveImpedanceAreasId;

    @Setter
    @Column(name = "inductive_resistance")
    private float inductiveResistance;

    public InductiveImpedanceAreas(float inductiveResistance) {
        this.inductiveResistance = inductiveResistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InductiveImpedanceAreas that = (InductiveImpedanceAreas) o;
        return inductiveImpedanceAreasId == that.inductiveImpedanceAreasId && Float.compare(inductiveResistance, that.inductiveResistance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inductiveImpedanceAreasId, inductiveResistance);
    }
}
