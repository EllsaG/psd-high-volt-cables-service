package com.project.psdhighvoltcablesservice.repository;

import com.project.psdhighvoltcablesservice.entity.HighVoltCables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighVoltCablesRepository extends JpaRepository<HighVoltCables,Short> {
}
