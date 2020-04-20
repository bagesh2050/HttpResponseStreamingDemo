package com.coreapi.stream.controller;

import java.io.ObjectOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.coreapi.stream.model.Employee;
import com.coreapi.stream.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeStreamController {

	private final Logger logger = LoggerFactory.getLogger(EmployeeStreamController.class);

	@Autowired
	private EmployeeRepository repository;

	@GetMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamingResponseBody> getAllEmployee() {

		StreamingResponseBody stream = outputStream -> {

			List<Employee> employeeList = repository.findAll();

			try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

				for (Employee employee : employeeList) {
					oos.write(employee.toString().getBytes());
				}
			}
		};

		logger.info("steaming response {} ", stream);
		return new ResponseEntity<>(stream, HttpStatus.OK);
	}
}