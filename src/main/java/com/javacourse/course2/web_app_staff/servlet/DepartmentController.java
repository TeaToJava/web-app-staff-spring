package com.javacourse.course2.web_app_staff.servlet;

import java.util.List;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javacourse.course2.web_app_staff.service.DepartmentService;
import com.javacourse.course2.web_app_staff.service.dto.DepartmentDto;
import com.javacourse.course2.web_app_staff.service.impl.ServiceException;

@RestController
@Validated
@RequestMapping("/department")
public class DepartmentController {

	private static final String ERROR_MESSAGE = "Department isn't found";
	private DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping()
	public List<DepartmentDto> getAllDepartments() {
		return departmentService.getAll();
	}

	@GetMapping("/{id}")
	public DepartmentDto getDepartment(@PathVariable("id") @org.hibernate.validator.constraints.UUID String id) throws ControllerException {
		try {
			UUID uuid = UUID.fromString(id);
			return departmentService.findById(uuid);
		} catch (ServiceException e) {
			throw new ControllerException(ERROR_MESSAGE);
		}
	}

	@PostMapping()
	public DepartmentDto createDepartment(@RequestBody DepartmentDto department) {
		return departmentService.save(department);
	}

	@PutMapping("/{id}")
	public DepartmentDto updateDepartment(@RequestBody DepartmentDto department, @PathVariable("id") @org.hibernate.validator.constraints.UUID String id) throws ControllerException {
		try {
			return departmentService.update(department);
		} catch (ServiceException e) {
			throw new ControllerException(ERROR_MESSAGE);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteDepartment(@PathVariable("id") @org.hibernate.validator.constraints.UUID String id) throws ControllerException {
		try {
			departmentService.delete(UUID.fromString(id));
		} catch (ServiceException e) {
			throw new ControllerException(ERROR_MESSAGE);
		}
	}

}