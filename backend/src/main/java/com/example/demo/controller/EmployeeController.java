package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRespository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRespository employeeRespository;
	
	//get all employees
	
	@GetMapping("/employees")
	public java.util.List<Employee> getAllEmployees(){
		return employeeRespository.findAll();
	}
	
	//create employee rest api
	
	@PostMapping("/employees")
	public Employee creatEmployee(@RequestBody @Valid Employee employee) {
		return employeeRespository.save(employee);
	}
	
	//get employee by ID rest API
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" +id));
		return ResponseEntity.ok(employee);
	}
	
	//update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		
		Employee employee = employeeRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" +id));
		employee.setFirstName(employeeDetails.getFirstName() != null ? employeeDetails.getFirstName() : employee.getFirstName());
		employee.setLastName(employeeDetails.getLastName() != null ? employeeDetails.getFirstName() : employee.getLastName());
		employee.setEmailId(employeeDetails.getEmailId() != null ? employeeDetails.getEmailId() : employee.getEmailId());
		
		Employee updatdEmployee = employeeRespository.save(employee);
		return ResponseEntity.ok(updatdEmployee);
	}
	
	//delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		
		Employee employee = employeeRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" +id));
		
		employeeRespository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
		
	}
	
	
}

