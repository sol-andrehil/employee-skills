package com.solstice.employee.solsticeemployee;

import com.solstice.employee.solsticeemployee.controller.EmployeeDetails;
import com.solstice.employee.solsticeemployee.model.Employee;
import com.solstice.employee.solsticeemployee.repository.EmployeeRepository;
import com.solstice.employee.solsticeemployee.service.EmployeeConverter;
import com.solstice.employee.solsticeemployee.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @Spy
    private EmployeeConverter converter = new EmployeeConverter();

    @InjectMocks
    private EmployeeService employeeService;

    @Before
    public void before() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Andre");
        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(repository.findAllById(ids)).thenReturn(employees);

        when(repository.findAll()).thenReturn(employees);
    }

    @Test
    public void getEmployeeNotNullTest() {
        Employee employee = employeeService.getEmployee(1L);
        Assert.assertNotNull(employee);
    }

    @Test
    public void getEmployeeTest() {
        Employee employee = employeeService.getEmployee(1L);
        Assert.assertEquals("Andre", employee.getFirstName());
    }

    @Test
    public void getEmployeesById() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        Iterable<Employee> employees = employeeService.getEmployeesByIds(ids);
        List<Employee> employeeList = new ArrayList<>();
        employees.iterator().forEachRemaining(employeeList::add);
        Assert.assertEquals(1, employeeList.size());
    }

    @Test
    public void getEmployees() {
        Iterable<EmployeeDetails> employees = employeeService.getEmployees();
        List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
        employees.iterator().forEachRemaining(employeeDetailsList::add);
        Assert.assertEquals(1, employeeDetailsList.size());
    }

}
