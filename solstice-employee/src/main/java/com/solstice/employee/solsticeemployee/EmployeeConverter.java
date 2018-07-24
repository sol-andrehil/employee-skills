package com.solstice.employee.solsticeemployee;

import org.springframework.stereotype.Service;

@Service
public class EmployeeConverter {

    public EmployeeDetails convert(Employee employee) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setId(employee.getId());
        employeeDetails.setRelativePath("employees/" + employee.getId());
        employeeDetails.setFirstName(employee.getFirstName());
        employeeDetails.setLastName(employee.getLastName());
        employeeDetails.setImageUrl(employee.getImageUrl());
        return employeeDetails;
    }

}
