package com.javacourse.course2.web_app_staff.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.javacourse.course2.web_app_staff.model.Department;
import com.javacourse.course2.web_app_staff.model.Employee;
import com.javacourse.course2.web_app_staff.model.Project;
import com.javacourse.course2.web_app_staff.repository.DepartmentRepository;
import com.javacourse.course2.web_app_staff.service.dto.DepartmentDto;
import com.javacourse.course2.web_app_staff.service.dto.mappers.DepartmentMapper;
import com.javacourse.course2.web_app_staff.service.impl.DepartmentServiceImpl;
import com.javacourse.course2.web_app_staff.service.impl.EmployeeServiceImpl;
import com.javacourse.course2.web_app_staff.service.impl.ProjectServiceImpl;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

	@Mock
	DepartmentRepository departmentRepository;

	private DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;
	private UUID randomUUID = UUID.randomUUID();
	private String name = "DepartmentName";
	private Department department = new Department(randomUUID, name);

	@Test
	void find_by_id() throws ServiceException {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(UUID.randomUUID(), "Employee1"));
		employees.add(new Employee(UUID.randomUUID(), "Employee2"));
		department.setEmployees(employees);
		Optional<Department> maybeDepartment = Optional.of(department);
		when(departmentRepository.findById(randomUUID)).thenReturn(maybeDepartment);
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		DepartmentDto departmentExpected = departmentService.findById(randomUUID);
		verify(departmentRepository).findById(randomUUID);

		assertEquals(department.getId(), departmentExpected.getId());
		assertEquals(department.getName(), departmentExpected.getName());
		assertEquals(department.getEmployees(), departmentExpected.getEmployees());
	}

	@Test
	void find_by_id_invalid() {
		Optional<Department> maybeDepartment = Optional.empty();
		when(departmentRepository.findById(randomUUID)).thenReturn(maybeDepartment);
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		assertThrows(ServiceException.class, () -> departmentService.findById(randomUUID));
	}

	@Test
	void update_department() throws ServiceException {
		when(departmentRepository.existsById(randomUUID)).thenReturn(true);
		when(departmentRepository.save(department)).thenReturn(department);
		DepartmentDto expected = new DepartmentDto();
		expected.setId(department.getId());
		expected.setName(department.getName());
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		departmentService.update(expected);
		verify(departmentRepository).save(department);

		assertEquals(department.getId(), expected.getId());
		assertEquals(department.getName(), expected.getName());
	}

	@Test
	void update_department_invalid_id() throws ServiceException {
		when(departmentRepository.existsById(randomUUID)).thenReturn(false);
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		DepartmentDto expected = new DepartmentDto();
		expected.setId(department.getId());
		expected.setName(department.getName());
		assertThrows(ServiceException.class, () -> departmentService.update(expected));
	}

	@Test
	void delete_department() throws ServiceException {
		when(departmentRepository.existsById(randomUUID)).thenReturn(true);
		doNothing().when(departmentRepository).deleteById(randomUUID);
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		departmentService.delete(randomUUID);
		verify(departmentRepository).deleteById(randomUUID);
	}

	@Test
	void delete_department_invalid() throws ServiceException {
		when(departmentRepository.existsById(randomUUID)).thenReturn(false);
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		assertThrows(ServiceException.class, () -> departmentService.delete(randomUUID));
	}

	@Test
	void save_department() {
		Department newDepartment = new Department(department.getName());
		when(departmentRepository.save(newDepartment)).thenReturn(department);
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		DepartmentDto departmentExpected = departmentService
				.save(departmentMapper.departmentToDepartmentDto(newDepartment));
		verify(departmentRepository).save(newDepartment);

		assertTrue(department.equals(departmentMapper.map(departmentExpected)));
	}

	@Test
	void get_all() {
		Department dep1 = new Department(UUID.randomUUID(), "Name");
		Department dep2 = new Department(UUID.randomUUID(), "Name 2");
		Employee employee = new Employee(UUID.randomUUID(), "Employee name");
		Project project = new Project(UUID.randomUUID(), "Project name", "Simple project");
		Project project2 = new Project(UUID.randomUUID(), "Project name 2", "Simple project 2");
		employee.setProjects(Arrays.asList(project, project2));
		dep1.setEmployees(Arrays.asList(employee));
		List<Department> departments = Arrays.asList(dep1, dep2);
		when(departmentRepository.findAll()).thenReturn(departments);
		DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
		List<DepartmentDto> deps = departmentService.getAll();

		verify(departmentRepository).findAll();
		assertEquals(departments.size(), deps.size());
		assertEquals(departments.get(0).getEmployees(), deps.get(0).getEmployees());
	}
}