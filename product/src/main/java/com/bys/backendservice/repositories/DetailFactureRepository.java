package com.bys.backendservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bys.backendservice.entities.DetailFacture;

@Repository
public interface DetailFactureRepository extends JpaRepository<DetailFacture, Long> {

}
