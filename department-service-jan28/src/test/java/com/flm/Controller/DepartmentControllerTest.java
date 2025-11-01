

package com.flm.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flm.controller.DepartmentController;
import com.flm.entity.Department;
import com.flm.exception.DepartmentNotFoundException;
import com.flm.service.DepartmentService;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    public void testGetDepartmentById() throws Exception {
        Long deptId = 1L;
        Department dept = Department.builder().id(deptId).name("IT").location("NY").build();
        when(departmentService.getDepartment(deptId)).thenReturn(dept);

        ResponseEntity<Department> response = departmentController.getDepartmentById(deptId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dept, response.getBody());
        verify(departmentService, times(1)).getDepartment(deptId);
    }

    @Test
    public void testGetDepartment_DepartmentNotFoundException() throws Exception {
        Long deptId = 99L;
        when(departmentService.getDepartment(deptId)).thenThrow(new DepartmentNotFoundException("Not found"));

        assertThrows(DepartmentNotFoundException.class, () -> {
            departmentController.getDepartment(deptId);
        });
        verify(departmentService, times(1)).getDepartment(deptId);
    }

    @Test
    public void testGetDepartments_ReturnsDepartmentList() throws Exception {
        List<Department> mockList = List.of(
            Department.builder().id(1L).name("IT").location("NY").build(),
            Department.builder().id(2L).name("HR").location("LA").build()
        );
        when(departmentService.getDepartment()).thenReturn(mockList);

        List<Department> result = departmentController.getDepartments();

        assertEquals(mockList, result);
        verify(departmentService, times(1)).getDepartment();
    }

    @Test
    public void testGetDepartments_DepartmentNotFoundException() throws Exception {
        when(departmentService.getDepartment()).thenThrow(new DepartmentNotFoundException("No departments found"));

        assertThrows(DepartmentNotFoundException.class, () -> {
            departmentController.getDepartments();
        });
        verify(departmentService, times(1)).getDepartment();
    }
}