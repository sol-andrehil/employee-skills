package com.solstice.employee.solsticeemployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeSkillService employeeSkillService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeSkillService employeeSkillService) {
        this.employeeService = employeeService;
        this.employeeSkillService = employeeSkillService;
    }

    @RequestMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping("/employees")
    public Iterable<EmployeeDetails> getEmployees(@RequestParam("skill") String skillName) {
        if (skillName != null) {
            return employeeSkillService.getEmployeesBySkill(skillName);
        }
        return employeeService.getEmployees();
    }
}
