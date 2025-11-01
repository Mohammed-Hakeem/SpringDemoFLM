package com.flm.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flm.entity.Department;
import com.flm.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

	@Mock
	DepartmentRepository repo;
	@InjectMocks
	DepartmentService service;
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public Department saveDepartmentSuccessTest(Department department){
		
	}
}
