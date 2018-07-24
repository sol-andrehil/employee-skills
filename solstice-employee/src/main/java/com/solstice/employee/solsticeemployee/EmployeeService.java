package com.solstice.employee.solsticeemployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {

    private EmployeeRepository repository;
    private EmployeeConverter employeeConverter;

    @Autowired
    public EmployeeService(EmployeeRepository repository, EmployeeConverter employeeConverter) {
        this.repository = repository;
        this.employeeConverter = employeeConverter;
    }

    public Employee getEmployee(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Iterable<EmployeeDetails> getEmployees() {
        Stream<Employee> employeeStream = StreamSupport.stream(repository.findAll().spliterator(), false);
        return employeeStream.map(employee -> employeeConverter.convert(employee)).collect(Collectors.toList());
    }

    public Iterable<Employee> getEmployeesByIds(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }
}
