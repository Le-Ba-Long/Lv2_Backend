package com.globits.da.repository;

import com.globits.da.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DistrictRepository extends JpaRepository<District, UUID> {
    @Query("SELECT d FROM District d  WHERE d.code = ?1")
    District findByCode(String code);

    Boolean existsDistrictById(UUID id);

    Boolean existsDistrictByCode(String code);

    Boolean existsDistrictByName(String name);
}
