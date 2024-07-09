package com.project.psdhighvoltcablesservice.repository;

import com.project.psdhighvoltcablesservice.entity.HighVoltCablesSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighVoltCablesSelectionRepository extends JpaRepository<HighVoltCablesSelection,Short> {
}
