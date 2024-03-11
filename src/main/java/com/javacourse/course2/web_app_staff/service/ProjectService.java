package com.javacourse.course2.web_app_staff.service;

import java.util.UUID;

import com.javacourse.course2.web_app_staff.service.dto.ProjectDto;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

public interface ProjectService {
	ProjectDto save(ProjectDto projectDto);

	ProjectDto findById(UUID uuid) throws ServiceException;

	ProjectDto update(ProjectDto projectDtO) throws ServiceException;

	void delete(UUID uuid) throws ServiceException;
}