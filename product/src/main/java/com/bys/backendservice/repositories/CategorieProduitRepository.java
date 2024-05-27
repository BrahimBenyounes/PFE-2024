
package com.bys.backendservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bys.backendservice.entities.CategorieProduit;

@Repository
public interface CategorieProduitRepository extends JpaRepository<CategorieProduit, Long>{

}
