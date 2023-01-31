package com.vorsin.businessProcess.repositories;

import com.vorsin.businessProcess.models.BusinessProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BPRepository extends JpaRepository<BusinessProcess, Integer> {

    Optional<BusinessProcess> findByTitle(String title);

    boolean existsByTitle(String title);



}
