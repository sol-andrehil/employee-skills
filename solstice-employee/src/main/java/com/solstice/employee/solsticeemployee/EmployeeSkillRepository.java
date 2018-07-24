package com.solstice.employee.solsticeemployee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSkillRepository extends CrudRepository<EmployeeSkill, Long> {

    Iterable<EmployeeSkill> findBySkillId(Long skillId);

}
