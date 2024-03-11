package com.javacourse.course2.web_app_staff.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="projects")
public class Project extends SimpleEntity {

	private static final long serialVersionUID = -853344416911951095L;
	private UUID projectId;
	private String name;
	private String description;
	private transient List<Employee> employees = new ArrayList<>();

	public Project() {

	}

	public Project(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Project(UUID id, String name, String description) {
		this.projectId = id;
		this.name = name;
		this.description = description;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	public UUID getId() {
		return projectId;
	}

	@Override
	public void setId(UUID id) {
		this.projectId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany
	@JoinTable(name = "employee_projects",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "employee_id"))
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, projectId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return Objects.equals(description, other.description) && Objects.equals(projectId, other.projectId)
				&& Objects.equals(name, other.name);
	}

}