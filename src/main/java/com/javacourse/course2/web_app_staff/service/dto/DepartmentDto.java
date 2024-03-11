package com.javacourse.course2.web_app_staff.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.javacourse.course2.web_app_staff.model.Employee;

public class DepartmentDto {

	private UUID id;
	private String name;
	private List<Employee> employees = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID randomUUID) {
		this.id = randomUUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
