package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.StageResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageResultRepository extends JpaRepository<StageResult, Integer> {

    Optional<StageResult> findByName(String name);

}
