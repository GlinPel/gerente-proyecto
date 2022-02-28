package com.gleenpeltroche.iniciosesionregistro.repositories;

import org.springframework.data.repository.CrudRepository;

import com.gleenpeltroche.iniciosesionregistro.models.Task;


public interface TaskRepository extends CrudRepository<Task, Long> {
	
}
