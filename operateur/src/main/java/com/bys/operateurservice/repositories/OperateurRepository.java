package com.bys.operateurservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bys.operateurservice.entities.Operateur;

@Repository
public interface OperateurRepository extends CrudRepository<Operateur, Long> {

}
