package com.javacourse.course2.web_app_staff.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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
import com.javacourse.course2.web_app_staff.repository.EmployeeRepository;
import com.javacourse.course2.web_app_staff.service.dto.EmployeeDto;
import com.javacourse.course2.web_app_staff.service.dto.mappers.EmployeeMapper;
import com.javacourse.course2.web_app_staff.service.impl.EmployeeServiceImpl;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;
import com.javacourse.course2.web_app_staff.servlet.ControllerException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	@Mock
	EmployeeRepository employeeRepository;
	@Mock
	DepartmentRepository departmentRepository;

	private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
	private UUID randomUUID = UUID.randomUUID();
	private String name = "Employee";
	private Department department = new Department(UUID.randomUUID(), "Department");
	private Employee employee = new Employee(name, department);

	@Test
	void find_by_id() throws ServiceException {
		employee.setId(randomUUID);
		employee.setDepartment(department);
		Optional<Employee> maybeEmployee = Optional.of(employee);
		when(employeeRepository.findById(randomUUID)).thenReturn(maybeEmployee);
		EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);
		EmployeeDto expectedEmployeeDto = employeeService.findById(randomUUID);
		verify(employeeRepository).findById(randomUUID);

		Employee expectedEmployee = employeeMapper.map(expectedEmployeeDto);
		assertEquals(employee, expectedEmployee);
	}

	@Test
	void find_by_id_invalid() {
		Optional<Employee> maybeEmployee = Optional.empty();
		when(employeeRepository.findById(randomUUID)).thenReturn(maybeEmployee);
		EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);
		assertThrows(ServiceException.class, () -> employeeService.findById(randomUUID));
	}

	@Test
	void save_employee() {
		Employee employee = new Employee("Name");
		Project project = new Project("New project", "Simple project");
		project.setId(UUID.randomUUID());
		employee.setProjects(Arrays.asList(project));
		Employee expectedEmployee = new Employee(UUID.randomUUID(), employee.getName());
		expectedEmployee.setProjects(employee.getProjects());
		when(employeeRepository.save(employee)).thenReturn(expectedEmployee);
		EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);
		EmployeeDto employeeExpectedDto = employeeService.save(employeeMapper.employeeToEmployeeDto(employee));
		verify(employeeRepository).save(employee);

		assertEquals(expectedEmployee.getName(), employeeExpectedDto.getName());
		assertEquals(expectedEmployee.getProjects(), employeeExpectedDto.getProjects());
		assertNotNull(employeeExpectedDto.getId());
	}
}