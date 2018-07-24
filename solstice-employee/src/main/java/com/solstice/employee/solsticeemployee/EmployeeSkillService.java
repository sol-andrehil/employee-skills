package com.solstice.employee.solsticeemployee;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EmployeeSkillService {

    private EmployeeService employeeService;
    private EmployeeSkillRepository repository;
    private EmployeeConverter employeeConverter;
    private EurekaClient discoveryClient;
    private RestTemplate restTemplate;

    @Autowired
    public EmployeeSkillService(EmployeeService employeeService, EmployeeSkillRepository repository, EmployeeConverter employeeConverter, EurekaClient discoveryClient) {
        this.employeeService = employeeService;
        this.repository = repository;
        this.employeeConverter = employeeConverter;
        this.discoveryClient = discoveryClient;
        this.restTemplate = new RestTemplate();
    }

    public Iterable<EmployeeDetails> getEmployeesBySkill(String skillName) {
        Skill skill = restTemplate.getForObject(fetchSkillServiceUrl() + "/skills/" + skillName, Skill.class);
        Iterable<EmployeeSkill> employeesBySkill = repository.findBySkillId(skill.getId());

        Stream<EmployeeSkill> stream = StreamSupport.stream(employeesBySkill.spliterator(), false);
        List<Long> employeesIds = stream.map(EmployeeSkill::getId).collect(Collectors.toList());

        Iterable<Employee> employees = employeeService.getEmployeesByIds(employeesIds);
        List<EmployeeDetails> result = new ArrayList<>();
        for (Employee employee : employees) {
            result.add(employeeConverter.convert(employee));
        }
        return result;
    }

    private String fetchSkillServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("SKILLS-SERVICE", false);
        return instance.getHomePageUrl();
    }
}
