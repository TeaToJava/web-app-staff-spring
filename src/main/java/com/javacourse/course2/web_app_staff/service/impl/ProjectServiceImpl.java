package com.javacourse.course2.web_app_staff.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacourse.course2.web_app_staff.model.Project;
import com.javacourse.course2.web_app_staff.model.Employee;
import com.javacourse.course2.web_app_staff.repository.ProjectRepository;
import com.javacourse.course2.web_app_staff.service.ProjectService;
import com.javacourse.course2.web_app_staff.service.dto.ProjectDto;
import com.javacourse.course2.web_app_staff.service.dto.mappers.ProjectMapper;

@Service
public class ProjectServiceImpl implements ProjectService {

	private ProjectRepository projectRepository;
	private ProjectMapper projectMapper = ProjectMapper.INSTANCE;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public ProjectDto findById(UUID uuid) throws ServiceException {
		Optional<Project> maybeProject = projectRepository.findById(uuid);
		if (maybeProject.isPresent()) {
			Project project = maybeProject.get();
			List<Employee> employees = project.getEmployees();
			project.setEmployees(employees);
			return projectMapper.projectToProjectDto(project);
		} else {
			throw new ServiceException();
		}
	}

	@Override
	@Transactional
	public ProjectDto save(ProjectDto projectDto) {
		Project project = projectRepository.save(projectMapper.map(projectDto));
		return projectMapper.projectToProjectDto(project);
	}

	@Override
	public ProjectDto update(ProjectDto projectDto) throws ServiceException {
		boolean isExisted = projectRepository.existsById(projectDto.getId());
		if (isExisted) {
			Project project = projectMapper.map(projectDto);
			return projectMapper.projectToProjectDto(projectRepository.save(project));
		} else {
			throw new ServiceException();
		}
	}

	@Override
	public void delete(UUID uuid) throws ServiceException {
		boolean isExisted = projectRepository.existsById(uuid);
		if (isExisted) {
			projectRepository.deleteById(uuid);
		} else {
			throw new ServiceException();
		}
	}

}