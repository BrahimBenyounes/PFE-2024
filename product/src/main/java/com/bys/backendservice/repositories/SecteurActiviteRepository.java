package com.bys.backendservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bys.backendservice.entities.SecteurActivite;

@Repository
public interface SecteurActiviteRepository extends CrudRepository<SecteurActivite, Long> {

}
