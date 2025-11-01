package com.flm.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.dto.EmployeeForm;
import com.flm.dto.Response;
import com.flm.entity.Employee;
import com.flm.exception.EmployeeNotFoundException;
import com.flm.service.EmployeeService;
import com.flm.util.Converter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	private final Converter converter;
	
	@PostMapping("/saveEmployee")
	public EmployeeForm saveEmployee(@RequestBody EmployeeForm employeeForm){
		Employee emp = converter.getEmployee(employeeForm);
		emp =  employeeService.saveEmployee(emp);
		return converter.getEmployeeForm(emp);
	}

	@GetMapping("/getEmployee/{id}")
	public Response getEmployee(@PathVariable Long id) throws EmployeeNotFoundException{
		return employeeService.getEmployee(id);
	}
	
	
	@GetMapping("/getEmployee/")
	public Response getEmployeeParam(@RequestParam("emp_id") Long id) throws EmployeeNotFoundException{
		return employeeService.getEmployee(id);
	}
	
	@GetMapping("/getEmployees/")
	public List<Response> getEmployees() throws EmployeeNotFoundException{
		return employeeService.getEmployeeDetails();
	}
	
//	private Response getEmployeeForm(Long id) throws EmployeeNotFoundException {
//		return employeeService.getEmployee(id);
//	}
	@DeleteMapping("/deleteEmployee/{id}")
	public String DeleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException{
		employeeService.deleteemployee(id);
		return "delete success";
	}
	@PutMapping("/updateEmployee/{id}")
	public Response updateEmployee(@PathVariable Long id,@RequestBody EmployeeForm employeeForm) throws EmployeeNotFoundException{
		return employeeService.updateEmployee(id,employeeForm);
	}
}
