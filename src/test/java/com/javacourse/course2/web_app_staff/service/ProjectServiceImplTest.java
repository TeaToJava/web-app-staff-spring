package com.javacourse.course2.web_app_staff.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javacourse.course2.web_app_staff.model.Project;

import com.javacourse.course2.web_app_staff.repository.ProjectRepository;
import com.javacourse.course2.web_app_staff.service.dto.ProjectDto;
import com.javacourse.course2.web_app_staff.service.dto.mappers.ProjectMapper;
import com.javacourse.course2.web_app_staff.service.impl.ProjectServiceImpl;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

	@Mock
	ProjectRepository projectRepository;

	private ProjectMapper projectMapper = ProjectMapper.INSTANCE;
	private UUID id = UUID.randomUUID();
	private static final String NAME = "New project";
	private static final String DESCRIPTION = "Simple project";
	private Project project = new Project(id, NAME, DESCRIPTION);

	@Test
	void find_by_id() throws ServiceException {
		Optional<Project> maybeProject = Optional.of(project);
		when(projectRepository.findById(id)).thenReturn(maybeProject);
		ProjectService projectService = new ProjectServiceImpl(projectRepository);
		ProjectDto expectedProject;
		expectedProject = projectService.findById(id);
		verify(projectRepository).findById(id);
		assertEquals(project, projectMapper.map(expectedProject));
	}

	@Test
	void find_by_id_invalid() throws ServiceException {
		Optional<Project> maybeProject = Optional.empty();
		when(projectRepository.findById(id)).thenReturn(maybeProject);
		ProjectService projectService = new ProjectServiceImpl(projectRepository);
		assertThrows(ServiceException.class, () -> projectService.findById(id));
	}

	@Test
	void save_project() {
		Project project = new Project(NAME, DESCRIPTION);
		Project expectedProject = project;
		when(projectRepository.save(project)).thenReturn(expectedProject);
		ProjectService projectService = new ProjectServiceImpl(projectRepository);
		ProjectDto projectExpectedDto = projectService.save(projectMapper.projectToProjectDto(project));
		verify(projectRepository).save(project);

		assertEquals(expectedProject, projectMapper.map(projectExpectedDto));
	}

	@Test
	void delete_project() throws ServiceException {
		when(projectRepository.existsById(id)).thenReturn(true);
		doNothing().when(projectRepository).deleteById(id);
		ProjectService projectService = new ProjectServiceImpl(projectRepository);
		projectService.delete(id);
		verify(projectRepository).deleteById(id);
	}

	@Test
	void delete_project_invalid() throws ServiceException {
		when(projectRepository.existsById(id)).thenReturn(false);
		ProjectService projectService = new ProjectServiceImpl(projectRepository);
		assertThrows(ServiceException.class, () -> projectService.delete(id));
	}

	@Test
	void update_project() throws ServiceException {
		when(projectRepository.existsById(id)).thenReturn(true);
		when(projectRepository.save(project)).thenReturn(project);
		ProjectService projectService = new ProjectServiceImpl(projectRepository);
		ProjectDto expected;
		expected = projectService.update(projectMapper.projectToProjectDto(project));
		verify(projectRepository).save(project);
		assertEquals(project.getId(), expected.getId());
		assertEquals(project.getName(), expected.getName());
		assertEquals(project.getDescription(), expected.getDescription());
	}

	@Test
	void update_project_invalid() throws ServiceException {
		when(projectRepository.existsById(id)).thenReturn(false);
		ProjectService projectService = new ProjectServiceImpl(projectRepository);
		ProjectDto projectDto = new ProjectDto();
		projectDto.setId(id);
		assertThrows(ServiceException.class, () -> projectService.update(projectDto));
	}
}