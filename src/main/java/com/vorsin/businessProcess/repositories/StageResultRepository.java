package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.StageResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StageResultRepository extends JpaRepository<StageResult, Integer> {

    Optional<StageResult> findByName(String name);

}
