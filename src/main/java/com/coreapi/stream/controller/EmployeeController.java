package com.coreapi.stream.controller;

import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.coreapi.stream.model.Employee;
import com.coreapi.stream.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeRepository repository;

	@GetMapping(value = "/employee/{lastname}")
	@Transactional
	public Stream<Employee> getAllEmployeesByLastName(@PathVariable("lastname") String lastName) {

		/*
		 * try (Stream<Employee> employee =
		 * repository.findAllByLastName(lastName)) { //
		 * Supplier<Stream<Employee>> streamSupplier = () -> employee; return
		 * employee; }
		 */

		Stream<Employee> employee = repository.findAllByLastName(lastName);

		return employee;
	}

	@GetMapping(value = "/employee")
	public List<Employee> getAllEmployees() {

		List<Employee> employee = repository.findAll();

		return employee;
	}

}
