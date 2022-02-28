package com.gleenpeltroche.iniciosesionregistro.repositories;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.gleenpeltroche.iniciosesionregistro.models.Project;



@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
 
 Optional<Project> findById(Long id);
 
 List<Project> findAll();
 
}


