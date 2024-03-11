package com.javacourse.course2.web_app_staff.service.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.javacourse.course2.web_app_staff.model.Department;
import com.javacourse.course2.web_app_staff.service.dto.DepartmentDto;

@Mapper
public interface DepartmentMapper {

	DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

	DepartmentDto departmentToDepartmentDto(Department department);
	
	Department map(DepartmentDto departmentDto);
}