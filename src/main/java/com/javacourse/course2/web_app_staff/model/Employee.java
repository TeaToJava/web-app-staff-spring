package com.javacourse.course2.web_app_staff.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee extends SimpleEntity {

	private static final long serialVersionUID = 8508454375561417073L;
	private UUID employeeId;
	private String name;
	private transient Department department;
	private List<Project> projects = new ArrayList<>();

	public Employee() {

	}

	public Employee(String name) {
		super();
		this.name = name;
	}

	public Employee(UUID id, String name) {
		this.employeeId = id;
		this.name = name;
	}

	public Employee(String name, Department department) {
		super();
		this.name = name;
		this.department = department;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	public UUID getId() {
		return employeeId;
	}

	@Override
	public void setId(UUID id) {
		this.employeeId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToMany
	@JoinTable(name = "employee_projects", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	@Fetch(FetchMode.JOIN)
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(employeeId, other.employeeId) && Objects.equals(name, other.name);
	}

}