package com.javacourse.course2.web_app_staff.service.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.javacourse.course2.web_app_staff.model.Employee;
import com.javacourse.course2.web_app_staff.service.dto.EmployeeDto;

@Mapper
public interface EmployeeMapper {

	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

	EmployeeDto employeeToEmployeeDto(Employee employee);

	Employee map(EmployeeDto employeeDto);
}