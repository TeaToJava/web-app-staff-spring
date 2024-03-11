package com.javacourse.course2.web_app_staff.service.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.javacourse.course2.web_app_staff.model.Project;
import com.javacourse.course2.web_app_staff.service.dto.ProjectDto;

@Mapper
public interface ProjectMapper {
	ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

	ProjectDto projectToProjectDto(Project project);

	Project map(ProjectDto projectDto);
}