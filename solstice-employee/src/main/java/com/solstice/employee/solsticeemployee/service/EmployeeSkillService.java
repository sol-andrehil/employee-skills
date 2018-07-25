package com.solstice.employee.solsticeemployee.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.solstice.employee.solsticeemployee.controller.EmployeeDetails;
import com.solstice.employee.solsticeemployee.repository.EmployeeSkillRepository;
import com.solstice.employee.solsticeemployee.controller.Skill;
import com.solstice.employee.solsticeemployee.model.Employee;
import com.solstice.employee.solsticeemployee.model.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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
    public EmployeeSkillService(EmployeeService employeeService, EmployeeSkillRepository repository, EmployeeConverter employeeConverter,
                                EurekaClient discoveryClient, RestTemplate restTemplate) {
        this.employeeService = employeeService;
        this.repository = repository;
        this.employeeConverter = employeeConverter;
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    public Iterable<EmployeeDetails> getEmployeesBySkill(String skillName) {
        Skill skill = getSkillByName(skillName);
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

    public Iterable<EmployeeDetails> getEmployeesByCapability(String capability) {
        Skill[] skills = restTemplate.getForObject(fetchSkillServiceUrl() + "/skills?capability=" + capability, Skill[].class);

        Stream<Skill> stream = Arrays.stream(skills);
        List<Long> skillIds = stream.map(Skill::getId).collect(Collectors.toList());

        Iterable<EmployeeSkill> employeesBySkill = repository.findBySkillId(skillIds);
        Stream<EmployeeSkill> employeeSkillStream = StreamSupport.stream(employeesBySkill.spliterator(), false);
        List<Long> employeesIds = employeeSkillStream.map(EmployeeSkill::getId).collect(Collectors.toList());

        Iterable<Employee> employees = employeeService.getEmployeesByIds(employeesIds);
        List<EmployeeDetails> result = new ArrayList<>();
        for (Employee employee : employees) {
            result.add(employeeConverter.convert(employee));
        }
        return result;
    }

    @Transactional
    public void addSkill(Long employeeId, String skillName) {
        Skill skill = getSkillByName(skillName);
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmployeeId(employeeId);
        employeeSkill.setSkillId(skill.getId());
        repository.save(employeeSkill);
    }

    private Skill getSkillByName(String skillName) {
        return restTemplate.getForObject(fetchSkillServiceUrl() + "/skills/" + skillName, Skill.class);
    }

    private String fetchSkillServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("SKILLS-SERVICE", false);
        return instance.getHomePageUrl();
    }
}
