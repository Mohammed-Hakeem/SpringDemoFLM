package com.flm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flm.entity.Department;
import com.flm.exception.DepartmentNotFoundException;
import com.flm.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	
	@Transactional
	public Department saveDepartment(Department department)throws DepartmentNotFoundException{
		return departmentRepository.save(department);
	}

	public Department getDepartment(Long id) throws DepartmentNotFoundException{
		Optional<Department> optDept = departmentRepository.findById(id);
		if (optDept.isPresent()) return optDept.get();
		throw new DepartmentNotFoundException("Department is not found!!");
	}
	
	public List<Department> getDepartment() throws DepartmentNotFoundException{
		Optional<List<Department>> optDept = Optional.ofNullable(departmentRepository.findAll());
		if (optDept.isPresent()) return optDept.get();
		throw new DepartmentNotFoundException("Department is not found!!");
	}

}
