package com.javacourse.course2.web_app_staff.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacourse.course2.web_app_staff.model.Employee;
import com.javacourse.course2.web_app_staff.repository.EmployeeRepository;
import com.javacourse.course2.web_app_staff.service.EmployeeService;
import com.javacourse.course2.web_app_staff.service.dto.EmployeeDto;

import com.javacourse.course2.web_app_staff.service.dto.mappers.EmployeeMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeDto findById(UUID uuid) throws ServiceException {
		Optional<Employee> maybeEmployee = employeeRepository.findById(uuid);
		if (maybeEmployee.isPresent()) {
			Employee employee = maybeEmployee.get();
			employee.setProjects(employee.getProjects());
			return employeeMapper.employeeToEmployeeDto(employee);
		} else {
			throw new ServiceException();
		}
	}

	@Override
	public EmployeeDto save(EmployeeDto employeeDto) {
		Employee employee = employeeRepository.save(employeeMapper.map(employeeDto));
		return employeeMapper.employeeToEmployeeDto(employee);
	}

}