package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.StageRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StageRelationRepository extends JpaRepository<StageRelation, Integer> {

    @Query(value = "SELECT *  FROM stage_relation WHERE from_stage =:fromStageId AND to_stage =:toStageId",
            nativeQuery = true)
    Optional<StageRelation> findStageRelationIfExists(@Param("fromStageId") int fromStageId, @Param("toStageId") int toStageId);

}
