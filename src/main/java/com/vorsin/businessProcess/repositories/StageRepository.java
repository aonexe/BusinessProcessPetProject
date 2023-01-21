package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
    List<Stage> findAllByBusinessProcess_Id(int id);

    Optional<Stage> findByBusinessProcess_IdAndId(int businessProcessId, int stageId);

}
