package com.javacourse.course2.web_app_staff.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.javacourse.course2.web_app_staff.model.Project;

public class EmployeeDto {

	private UUID id;
	private String name;
	private List<Project> projects = new ArrayList<>();

	public EmployeeDto() {

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}