package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.ActionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionResultRepository extends JpaRepository<ActionResult, Integer> {

    Optional<ActionResult> findByName(String name);

}
