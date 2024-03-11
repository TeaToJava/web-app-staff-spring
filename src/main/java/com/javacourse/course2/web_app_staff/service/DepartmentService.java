package com.javacourse.course2.web_app_staff.service;

import java.util.List;
import java.util.UUID;

import com.javacourse.course2.web_app_staff.service.dto.DepartmentDto;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

public interface DepartmentService {
	DepartmentDto save(DepartmentDto departmentDto);

	DepartmentDto findById(UUID uuid) throws ServiceException;

	DepartmentDto update(DepartmentDto departmentDto) throws ServiceException;

	void delete(UUID uuid) throws ServiceException;

	List<DepartmentDto> getAll();

}