package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {

}
