package com.gleenpeltroche.iniciosesionregistro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gleenpeltroche.iniciosesionregistro.models.Project;
import com.gleenpeltroche.iniciosesionregistro.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
    private ProjectRepository projectRepo;
	
	public List<Project> getAllProjects(){
		List<Project> projects = projectRepo.findAll();
		return projects;
	}
	
	public Project findProjectById(Long id){
		Optional<Project> potentialProject = projectRepo.findById(id);
		if(potentialProject.isPresent()) {
			return potentialProject.get();
		}else {
			return null;
		}
	}
	
	public void saveProject(Project project){
		projectRepo.save(project);	
	}
	
	public void deleteProject(Long id){
		projectRepo.deleteById(id);
	}

}
