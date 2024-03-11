--liquibase formatted sql

--changeset teatojava:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS projects(
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
name VARCHAR(250) UNIQUE,
description TEXT);

--changeset teatojava:2
CREATE TABLE IF NOT EXISTS department(
id UUID PRIMARY KEY DEFAULT gen_random_uuid() UNIQUE,
name VARCHAR(250) UNIQUE);

--changeset teatojava:3
CREATE TABLE IF NOT EXISTS employee(
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
name VARCHAR(250) UNIQUE,
department_id UUID,
	CONSTRAINT fk_department
      FOREIGN KEY(department_id) 
	  REFERENCES department(id)
	  ON DELETE SET NULL
);

--changeset teatojava:4
CREATE TABLE IF NOT EXISTS employee_projects(
employee_id UUID REFERENCES employee(id),
project_id UUID REFERENCES projects(id) ON DELETE CASCADE,
CONSTRAINT employee_projects_pk PRIMARY KEY(employee_id,project_id),
CONSTRAINT uc_key UNIQUE (employee_id,project_id));

--changeset teatojava:5
INSERT INTO department(name) VALUES ('Department 1'), ('Department 2')
ON CONFLICT (name) DO NOTHING;

--changeset teatojava:6
INSERT INTO projects(name, description) 
VALUES ('Project 1', 'Web site for real estate'),
('Project 2', 'Mobile internet banking')
ON CONFLICT (name) DO NOTHING;

--changeset teatojava:7
INSERT INTO employee(name) 
VALUES ('Employee 1'),
('Employee 2'), 
('Employee 3')
ON CONFLICT (name) DO NOTHING;

--changeset teatojava:8
UPDATE employee
 SET department_id = 
 (SELECT id from department 
 WHERE name='Department 1')
 WHERE name = 'Employee 1';

--changeset teatojava:9 
UPDATE employee
 SET department_id = 
 (SELECT id from department 
 WHERE name='Department 1')
 WHERE name = 'Employee 2';

--changeset teatojava:10
UPDATE employee
 SET department_id = 
 (SELECT id from department 
 WHERE name='Department 2')
 WHERE name = 'Employee 3';

--changeset teatojava:11
INSERT INTO employee_projects (employee_id, project_id)
 VALUES ((SELECT id FROM employee WHERE name = 'Employee 2'), 
		(SELECT id FROM projects WHERE name = 'Project 2'))
		ON CONFLICT(employee_id,project_id) DO NOTHING;

--changeset teatojava:12
INSERT INTO employee_projects (employee_id, project_id)
 VALUES ((SELECT id FROM employee WHERE name = 'Employee 1'), 
		(SELECT id FROM projects WHERE name = 'Project 1'))
		ON CONFLICT(employee_id,project_id) DO NOTHING;