package com.coreapi.stream.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coreapi.stream.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Stream<Employee> findAllByLastName(String lastname);
	
	List<Employee> findAll();

}
