package com.javacourse.course2.web_app_staff.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacourse.course2.web_app_staff.model.Department;
import com.javacourse.course2.web_app_staff.model.Employee;
import com.javacourse.course2.web_app_staff.repository.DepartmentRepository;
import com.javacourse.course2.web_app_staff.service.DepartmentService;
import com.javacourse.course2.web_app_staff.service.dto.DepartmentDto;
import com.javacourse.course2.web_app_staff.service.dto.mappers.DepartmentMapper;

@Service

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentRepository departmentRepository;
	private DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;

	@Autowired
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	@Transactional
	public DepartmentDto save(DepartmentDto departmentDto) {
		Department department = departmentRepository.save(departmentMapper.map(departmentDto));
		return departmentMapper.departmentToDepartmentDto(department);
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentDto findById(UUID uuid) throws ServiceException {
		Optional<Department> department = departmentRepository.findById(uuid);
		if (department.isPresent()) {
			Department dep = department.get();
			List<Employee> employees = dep.getEmployees();
			employees.stream().forEach(e -> e.setProjects(e.getProjects()));
			dep.setEmployees(employees);
			return departmentMapper.departmentToDepartmentDto(dep);
		} else {
			throw new ServiceException();
		}
	}

	@Override
	@Transactional
	public DepartmentDto update(DepartmentDto departmentDto) throws ServiceException {
		boolean isExisted = departmentRepository.existsById(departmentDto.getId());
		if (isExisted) {
			Department department = departmentMapper.map(departmentDto);
			return departmentMapper.departmentToDepartmentDto(departmentRepository.save(department));
		} else {
			throw new ServiceException();
		}
	}

	@Override
	@Transactional
	public void delete(UUID uuid) throws ServiceException {
		boolean isExisted = departmentRepository.existsById(uuid);
		if (isExisted) {
			departmentRepository.deleteById(uuid);
		}else {
			throw new ServiceException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartmentDto> getAll() {
		return departmentRepository.findAll().stream().map(d -> departmentMapper.departmentToDepartmentDto(d))
				.toList();
	}

}