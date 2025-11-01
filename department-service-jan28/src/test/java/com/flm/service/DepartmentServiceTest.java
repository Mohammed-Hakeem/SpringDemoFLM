package com.flm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flm.entity.Department;
import com.flm.exception.DepartmentNotFoundException;
import com.flm.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

	@Mock
	DepartmentRepository repo;
	@InjectMocks
	DepartmentService service;

	@Test
	void saveDepartmentSuccessTest() throws DepartmentNotFoundException {
		Department dept = Department.builder().id(1L).name("IT").location("NY").build();
		when(repo.save(dept)).thenReturn(dept);
		Department result = service.saveDepartment(dept);
		assertEquals(dept, result);
		verify(repo, times(1)).save(dept);
	}

	@Test
	void getDepartmentByIdSuccessTest() throws Exception {
		Department dept = Department.builder().id(1L).name("IT").location("NY").build();
		when(repo.findById(1L)).thenReturn(Optional.of(dept));
		Department result = service.getDepartment(1L);
		assertEquals(dept, result);
		verify(repo, times(1)).findById(1L);
	}

	@Test
	void getDepartmentByIdNotFoundTest() {
		when(repo.findById(2L)).thenReturn(Optional.empty());
		Exception exception = assertThrows(DepartmentNotFoundException.class, () -> service.getDepartment(2L));
		assertEquals("Department is not found!!", exception.getMessage());
	}

	@Test
	void getAllDepartmentsSuccessTest() throws Exception {
		List<Department> depts = Arrays.asList(
			Department.builder().id(1L).name("IT").location("NY").build(),
			Department.builder().id(2L).name("HR").location("LA").build()
		);
		when(repo.findAll()).thenReturn(depts);
		List<Department> result = service.getDepartment();
		assertEquals(depts, result);
		verify(repo, times(1)).findAll();
	}

	@Test
	void getAllDepartmentsNotFoundTest() {
		when(repo.findAll()).thenReturn(null);
		Exception exception = assertThrows(DepartmentNotFoundException.class, () -> service.getDepartment());
		assertEquals("Department is not found!!", exception.getMessage());
	}
}
