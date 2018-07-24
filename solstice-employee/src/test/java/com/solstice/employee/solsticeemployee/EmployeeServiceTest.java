package com.solstice.employee.solsticeemployee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService employeeService;

    @Before
    public void before() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Andre");
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
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

}
