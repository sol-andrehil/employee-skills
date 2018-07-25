package com.solstice.employee.solsticeemployee.controller;

import com.solstice.employee.solsticeemployee.service.EmployeeService;
import com.solstice.employee.solsticeemployee.service.EmployeeSkillService;
import com.solstice.employee.solsticeemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeSkillService employeeSkillService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeSkillService employeeSkillService) {
        this.employeeService = employeeService;
        this.employeeSkillService = employeeSkillService;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/employees")
    public Iterable<EmployeeDetails> getEmployees(@RequestParam(value="skill", required = false) String skillName, @RequestParam(value="capability", required = false) String capability) {

        if (skillName != null) {
            return employeeSkillService.getEmployeesBySkill(skillName);
        }

        if (capability != null) {
            return employeeSkillService.getEmployeesByCapability(capability);
        }
        return employeeService.getEmployees();
    }

    @PostMapping("/employees/{id}/skills")
    public void addNewSkill(@PathVariable Long id, String skillName) {
        employeeSkillService.addSkill(id, skillName);
    }

}
