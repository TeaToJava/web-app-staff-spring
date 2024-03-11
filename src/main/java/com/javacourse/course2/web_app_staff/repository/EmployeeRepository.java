package com.javacourse.course2.web_app_staff.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javacourse.course2.web_app_staff.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

}