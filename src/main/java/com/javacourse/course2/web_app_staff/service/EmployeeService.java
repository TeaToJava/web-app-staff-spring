package com.javacourse.course2.web_app_staff.service;

import java.util.UUID;

import com.javacourse.course2.web_app_staff.service.dto.EmployeeDto;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

public interface EmployeeService {

	EmployeeDto save(EmployeeDto employeeDto);

	EmployeeDto findById(UUID uuid) throws ServiceException;

}