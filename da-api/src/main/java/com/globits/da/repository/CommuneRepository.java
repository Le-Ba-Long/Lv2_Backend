package com.globits.da.repository;

import com.globits.da.domain.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, UUID> {
    @Query("SELECT c FROM Commune c WHERE c.code = ?1")
    Commune findByCode(String code);

    Boolean existsCommuneById(UUID id);

    Boolean existsCommuneByCode(String code);

    Boolean existsCommuneByName(String name);
}
