package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {

    @Query("SELECT businessProcess.id FROM Stage WHERE id =:stageId")
    int findProcessIdByStageId(@Param("stageId") int stageId);

}
