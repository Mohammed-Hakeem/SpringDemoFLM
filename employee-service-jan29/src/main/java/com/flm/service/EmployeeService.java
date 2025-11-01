package com.flm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.flm.dto.DepartmentForm;
import com.flm.dto.EmployeeForm;
import com.flm.dto.Response;
import com.flm.entity.Employee;
import com.flm.exception.EmployeeNotFoundException;
import com.flm.repository.EmployeeRepository;
import com.flm.util.Converter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final RestTemplate restTemplate;
	private final Converter converter;

	@Transactional
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Response getEmployee(Long id) throws EmployeeNotFoundException {
		Response response = new Response();
		EmployeeForm eForm = null;
		Optional<Employee> optEmp = employeeRepository.findById(id);
		if (optEmp.isPresent()) {
			eForm = converter.getEmployeeForm(optEmp.get());
			response.setEmployeeForm(eForm);
		} else
			throw new EmployeeNotFoundException("Employee is not found!!");
		DepartmentForm departmentForm = restTemplate.getForObject(
				"http://DEPARTMENT-SERVICE/dept/getDepartment/" + eForm.getDeptId(), DepartmentForm.class);
		response.setDepartmentForm(departmentForm);
		return response;
	}

	public List<Response> getEmployeeDetails() throws EmployeeNotFoundException {

		List<Employee> emplist = employeeRepository.findAll();

		List<Response> responselist = new ArrayList<>();

		List<EmployeeForm> employeeformlist = new ArrayList<>();
		for (Employee foundemp : emplist) {
			if (foundemp != null) {
				ResponseEntity<DepartmentForm> department = restTemplate.getForEntity(
						"http://department-service/dept/getDepartment/" + foundemp.getDeptId(), DepartmentForm.class);
				DepartmentForm departmentform = department.getBody();

				Response response = new Response();

				EmployeeForm empform = new EmployeeForm();
				empform.setId(foundemp.getId());
				empform.setName(foundemp.getName());
				empform.setAddress(foundemp.getAddress());
				empform.setSalary(foundemp.getSalary());
				empform.setDeptId(foundemp.getDeptId());

				response.setEmployeeForm(empform);
				response.setDepartmentForm(departmentform);

				employeeformlist.add(empform);
				
				responselist.add(response);

			} else if (employeeformlist.isEmpty()) {
				{
					throw new EmployeeNotFoundException("No employees  found!!");
				}

			} else {
				throw new EmployeeNotFoundException("Employee is not found!!");
			}
		}
		return responselist;
	}

	public void deleteemployee(Long id) {
		
		employeeRepository.deleteById(id);
		
		
	}
	
	public Response updateEmployee(Long id,EmployeeForm emp) throws EmployeeNotFoundException {
		Response response = new Response();
		EmployeeForm eForm = null;
		Optional<Employee> optEmp = employeeRepository.findById(id);
		if (optEmp.isPresent()) {
			eForm = converter.getEmployeeForm(optEmp.get());
			eForm.setAddress(emp.getAddress());
			response.setEmployeeForm(eForm);
		} else
			throw new EmployeeNotFoundException("Employee is not found!!");
		
		DepartmentForm departmentForm = restTemplate.getForObject(
				"http://DEPARTMENT-SERVICE/dept/getDepartment/" + emp.getDeptId(), DepartmentForm.class);
		response.setDepartmentForm(departmentForm);
		return response;
	}


}
