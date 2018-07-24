package com.solstice.employee.solsticeemployee;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeSkill {

    @Id
    private Long id;
    private Long employeeId;
    private Long skillId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
