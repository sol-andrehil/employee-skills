package com.solstice.employee.solsticeemployee;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.solstice.employee.solsticeemployee.controller.EmployeeDetails;
import com.solstice.employee.solsticeemployee.controller.Skill;
import com.solstice.employee.solsticeemployee.model.Employee;
import com.solstice.employee.solsticeemployee.model.EmployeeSkill;
import com.solstice.employee.solsticeemployee.repository.EmployeeRepository;
import com.solstice.employee.solsticeemployee.repository.EmployeeSkillRepository;
import com.solstice.employee.solsticeemployee.service.EmployeeConverter;
import com.solstice.employee.solsticeemployee.service.EmployeeService;
import com.solstice.employee.solsticeemployee.service.EmployeeSkillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeSkillServiceTests {

    @Mock
    private EmployeeSkillRepository skillsRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private EurekaClient discoveryClient;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private InstanceInfo instance;

    @Spy
    private EmployeeConverter converter = new EmployeeConverter();

    @Spy
    @InjectMocks
    private EmployeeService employeeService = new EmployeeService();
    @InjectMocks
    private EmployeeSkillService employeeSkillService;

    @Before
    public void before() {
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setId(1L);
        employeeSkill.setSkillId(1L);
        employeeSkill.setEmployeeId(1L);

        Skill skill = new Skill();
        skill.setId(1L);
        skill.setSkillName("spring-boot");
        skill.setCapability("technical");

        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        employeeSkills.add(employeeSkill);

        when(skillsRepository.findBySkillId(1L)).thenReturn(employeeSkills);
        List<Long> skillIds = new ArrayList<>();
        skillIds.add(1L);
        when(skillsRepository.findBySkillId(skillIds)).thenReturn(employeeSkills);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Andre");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(employeeRepository.findAllById(ids)).thenReturn(employees);

        when(instance.getHomePageUrl()).thenReturn("a");
        when(discoveryClient.getNextServerFromEureka("SKILLS-SERVICE", false)).thenReturn(instance);
        when(restTemplate.getForObject("a/skills/spring-boot", Skill.class)).thenReturn(skill);
        when(restTemplate.getForObject("a/skills?capability=technical", Skill[].class)).thenReturn(new Skill[] { skill });
    }


    @Test
    public void getEmployeesBySkill() {
        Iterable<EmployeeDetails> employeesBySkill = employeeSkillService.getEmployeesBySkill("spring-boot");
        List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
        employeesBySkill.iterator().forEachRemaining(employeeDetailsList::add);
        assertEquals(1, employeeDetailsList.size());
    }

    @Test
    public void getEmployeesByCapability() {
        Iterable<EmployeeDetails> employeesBySkill = employeeSkillService.getEmployeesByCapability("technical");
        List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
        employeesBySkill.iterator().forEachRemaining(employeeDetailsList::add);
        assertEquals(1, employeeDetailsList.size());
    }

    @Test
    public void addSkill() {
        employeeSkillService.addSkill(1L, "spring-boot");
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmployeeId(1L);
        employeeSkill.setSkillId(1L);
        verify(skillsRepository, times(1)).save(refEq(employeeSkill));
    }

}
