package com.javacourse.course2.web_app_staff.servlet;

import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javacourse.course2.web_app_staff.service.ProjectService;
import com.javacourse.course2.web_app_staff.service.dto.ProjectDto;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

@RestController
@Validated
@RequestMapping("/project")
public class ProjectController {

	private static final String ID = "id";
	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping("/{id}")
	public ProjectDto getProject(@PathVariable(ID) @org.hibernate.validator.constraints.UUID String id) throws ControllerException {
		try{
			UUID uuid = UUID.fromString(id);
			return projectService.findById(uuid);
		}catch(ServiceException exception) {
			throw new ControllerException("Project isn't found");
		}
	}

	@PostMapping()
	public ProjectDto createProject(@RequestBody ProjectDto project) {
		return projectService.save(project);
	}

	@PutMapping("/{id}")
	public ProjectDto updateProject(@RequestBody ProjectDto project,
			@PathVariable(ID) @org.hibernate.validator.constraints.UUID String id) throws ControllerException {
		try {
			return projectService.update(project);
		} catch (ServiceException e) {
			throw new ControllerException("Can't update project");
		}
	}

	@DeleteMapping("/{id}")
	public void deleteProject(@PathVariable(ID) @org.hibernate.validator.constraints.UUID String id) throws ControllerException {
		try {
			projectService.delete(UUID.fromString(id));
		} catch (ServiceException e) {
			throw new ControllerException("Can't delete project");
		}
	}

}