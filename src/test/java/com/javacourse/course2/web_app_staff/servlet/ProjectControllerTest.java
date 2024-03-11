package com.javacourse.course2.web_app_staff.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javacourse.course2.web_app_staff.service.ProjectService;
import com.javacourse.course2.web_app_staff.service.dto.ProjectDto;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

@ExtendWith(MockitoExtension.class)

class ProjectControllerTest {

	@Mock
	ProjectService projectService;

	private UUID randomUUID = UUID.randomUUID();
	private String uuidAsString = randomUUID.toString();

	@Test
	void test_get_project() throws ServiceException, ControllerException {
		ProjectDto projectDto = createProjectDto();
		when(projectService.findById(randomUUID)).thenReturn(projectDto);
		ProjectController projectController = new ProjectController(projectService);
		ProjectDto project = null;
		project = projectController.getProject(uuidAsString);
		verify(projectService).findById(randomUUID);
		assertEquals(projectDto.getId(), project.getId());
		assertEquals(projectDto.getName(), project.getName());
		assertEquals(projectDto.getDescription(), project.getDescription());
	}

	@Test
	void test_update_project() throws ServiceException, ControllerException {
		ProjectDto projectDto = createProjectDto();
		when(projectService.update(projectDto)).thenReturn(projectDto);
		ProjectController projectController = new ProjectController(projectService);
		ProjectDto project = projectController.updateProject(projectDto, uuidAsString);
		verify(projectService).update(projectDto);

		assertEquals(projectDto.getId(), project.getId());
		assertEquals(projectDto.getName(), project.getName());
		assertEquals(projectDto.getDescription(), project.getDescription());
	}

	@Test
	void test_update_project_invalid() throws ServiceException, ControllerException {
		ProjectDto projectDto = createProjectDto();
		when(projectService.update(projectDto)).thenThrow(ServiceException.class);
		ProjectController projectController = new ProjectController(projectService);

		assertThrows(ControllerException.class, () -> projectController.updateProject(projectDto, uuidAsString));
	}

	@Test
	void test_create_project() {
		ProjectDto projectDto = createProjectDto();
		ProjectDto fromProject = new ProjectDto();
		fromProject.setName(projectDto.getName());
		when(projectService.save(fromProject)).thenReturn(projectDto);
		ProjectController projectController = new ProjectController(projectService);
		ProjectDto project = projectController.createProject(fromProject);
		verify(projectService).save(fromProject);

		assertEquals(projectDto.getId(), project.getId());
		assertEquals(projectDto.getName(), project.getName());
	}

	@Test
	void test_get_project_invalid() throws ServiceException, ControllerException {
		when(projectService.findById(randomUUID)).thenThrow(ServiceException.class);
		ProjectController projectController = new ProjectController(projectService);

		assertThrows(ControllerException.class, () -> projectController.getProject(uuidAsString));
	}

	@Test
	void delete_project() throws ServiceException, ControllerException {
		doNothing().when(projectService).delete(randomUUID);
		ProjectController projectController = new ProjectController(projectService);
		projectController.deleteProject(uuidAsString);
		verify(projectService).delete(randomUUID);
	}

	@Test
	void delete_project_invalid() throws ServiceException, ControllerException {
		doThrow(ServiceException.class).when(projectService).delete(randomUUID);
		ProjectController projectController = new ProjectController(projectService);

		assertThrows(ControllerException.class, () -> projectController.deleteProject(uuidAsString));
	}

	private ProjectDto createProjectDto() {
		ProjectDto projectDto = new ProjectDto();
		String name = "ProjectName " + new Random(50).nextInt();
		projectDto.setId(UUID.randomUUID());
		projectDto.setName(name);
		projectDto.setDescription("Web app");
		return projectDto;
	}

}