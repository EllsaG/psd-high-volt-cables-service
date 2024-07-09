package com.project.psdhighvoltcablesservice.repository;

import com.project.psdhighvoltcablesservice.entity.HighVoltInformationInductiveImpedanceAreas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HighVoltInformationInductiveImpedanceAreasRepository
        extends JpaRepository <HighVoltInformationInductiveImpedanceAreas, Short>{

     boolean existsByHighVoltInformationIdFk(short highVoltInformationIds);
     void deleteAllByHighVoltInformationIdFk(short highVoltInformationIdFk);
     List<HighVoltInformationInductiveImpedanceAreas> findByHighVoltInformationIdFk(short highVoltInformationIdFk);
}
