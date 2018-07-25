package com.solstice.employee.solsticeemployee.repository;

import com.solstice.employee.solsticeemployee.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
