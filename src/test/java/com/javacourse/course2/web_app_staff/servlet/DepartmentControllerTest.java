package com.javacourse.course2.web_app_staff.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javacourse.course2.web_app_staff.service.DepartmentService;
import com.javacourse.course2.web_app_staff.service.dto.DepartmentDto;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

@ExtendWith(MockitoExtension.class)

class DepartmentControllerTest {

	@Mock
	DepartmentService departmentService;

	private UUID randomUUID = UUID.randomUUID();
	private String uuidAsString = randomUUID.toString();

	@Test
	void test_get_department() throws ServiceException, ControllerException {
		DepartmentDto departmentDto = createDepartmentDto();
		when(departmentService.findById(randomUUID)).thenReturn(departmentDto);
		DepartmentController departmentController = new DepartmentController(departmentService);
		DepartmentDto department = departmentController.getDepartment(uuidAsString);
		verify(departmentService).findById(randomUUID);

		assertEquals(departmentDto.getId(), department.getId());
		assertEquals(departmentDto.getName(), department.getName());
	}

	@Test
	void test_get_department_invalid() throws ServiceException, ControllerException {
		when(departmentService.findById(randomUUID)).thenThrow(ServiceException.class);
		DepartmentController departmentController = new DepartmentController(departmentService);

		assertThrows(ControllerException.class, () -> departmentController.getDepartment(uuidAsString));
	}

	@Test
	void test_update_department() throws ServiceException, ControllerException {
		DepartmentDto departmentDto = createDepartmentDto();
		when(departmentService.update(departmentDto)).thenReturn(departmentDto);
		DepartmentController departmentController = new DepartmentController(departmentService);
		DepartmentDto department = departmentController.updateDepartment(departmentDto, uuidAsString);
		verify(departmentService).update(departmentDto);

		assertEquals(departmentDto.getId(), department.getId());
		assertEquals(departmentDto.getName(), department.getName());
	}

	@Test
	void test_update_department_invalid() throws ServiceException, ControllerException {
		DepartmentDto departmentDto = createDepartmentDto();
		when(departmentService.update(departmentDto)).thenThrow(ServiceException.class);
		DepartmentController departmentController = new DepartmentController(departmentService);

		assertThrows(ControllerException.class, () -> departmentController.updateDepartment(departmentDto, uuidAsString));
	}

	@Test
	void test_create_department() {
		DepartmentDto departmentDto = createDepartmentDto();
		DepartmentDto fromDepartment = new DepartmentDto();
		fromDepartment.setName(departmentDto.getName());
		when(departmentService.save(fromDepartment)).thenReturn(departmentDto);
		DepartmentController departmentController = new DepartmentController(departmentService);
		DepartmentDto department = departmentController.createDepartment(fromDepartment);
		verify(departmentService).save(fromDepartment);

		assertEquals(departmentDto.getId(), department.getId());
		assertEquals(departmentDto.getName(), department.getName());
	}

	@Test
	void delete_department() throws ServiceException, ControllerException {
		doNothing().when(departmentService).delete(randomUUID);
		DepartmentController departmentController = new DepartmentController(departmentService);
		departmentController.deleteDepartment(uuidAsString);
		verify(departmentService).delete(randomUUID);
	}

	@Test
	void delete_department_invalid() throws ServiceException, ControllerException {
		doThrow(ServiceException.class).when(departmentService).delete(randomUUID);
		DepartmentController departmentController = new DepartmentController(departmentService);

		assertThrows(ControllerException.class, () -> departmentController.deleteDepartment(uuidAsString));
	}

	@Test
	void get_all_departments() {
		DepartmentDto dep1 = createDepartmentDto();
		DepartmentDto dep2 = createDepartmentDto();
		List<DepartmentDto> departments = Arrays.asList(dep1, dep2);
		when(departmentService.getAll()).thenReturn(departments);
		DepartmentController departmentController = new DepartmentController(departmentService);
		departmentController.getAllDepartments();
		verify(departmentService).getAll();
	}

	private DepartmentDto createDepartmentDto() {
		DepartmentDto departmentDto = new DepartmentDto();
		String name = "DepartmentName " + new Random(50).nextInt();
		departmentDto.setId(UUID.randomUUID());
		departmentDto.setName(name);
		return departmentDto;
	}
}