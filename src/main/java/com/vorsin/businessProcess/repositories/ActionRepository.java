package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {
}
