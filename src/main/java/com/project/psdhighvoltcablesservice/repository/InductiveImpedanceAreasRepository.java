package com.project.psdhighvoltcablesservice.repository;

import com.project.psdhighvoltcablesservice.entity.InductiveImpedanceAreas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InductiveImpedanceAreasRepository extends JpaRepository<InductiveImpedanceAreas,Short> {


    void deleteAllByInductiveImpedanceAreasIdIn(List<Short> inductiveImpedanceAreasId);
    List<InductiveImpedanceAreas> findByInductiveImpedanceAreasIdIn(List<Short> inductiveImpedanceAreasId);
}
