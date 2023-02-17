package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.ActionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionResultRepository extends JpaRepository<ActionResult, Integer> {

    Optional<ActionResult> findByName(String name);

}
