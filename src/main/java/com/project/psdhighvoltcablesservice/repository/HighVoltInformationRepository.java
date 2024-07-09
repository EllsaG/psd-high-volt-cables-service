package com.project.psdhighvoltcablesservice.repository;

import com.project.psdhighvoltcablesservice.entity.HighVoltInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighVoltInformationRepository extends JpaRepository<HighVoltInformation,Short> {
}
