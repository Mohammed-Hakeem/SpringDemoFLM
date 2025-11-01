package com.flm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flm.dto.DepartmentForm;
import com.flm.entity.Department;
import com.flm.exception.DepartmentNotFoundException;
import com.flm.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	@PostMapping("/saveDepartment")
	public Department saveDepartment(@RequestBody DepartmentForm departmentForm) throws DepartmentNotFoundException{
		Department dept = Department.builder()
				.name(departmentForm.getName())
				.location(departmentForm.getLocation())
				.build();
		return departmentService.saveDepartment(dept);
	}

	@GetMapping("/getDepartment/{id}")
	public ResponseEntity<Department> getDepartment(@PathVariable Long id) throws DepartmentNotFoundException{
		return new ResponseEntity<>(departmentService.getDepartment(id),HttpStatus.OK);
	}
	
	
	@GetMapping("/getDepartment")
	public List<Department> getDepartments() throws DepartmentNotFoundException{
		return departmentService.getDepartment();
	}
	
}
