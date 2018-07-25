package com.solstice.employee.solsticeemployee.repository;

import com.solstice.employee.solsticeemployee.model.EmployeeSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSkillRepository extends CrudRepository<EmployeeSkill, Long> {

    Iterable<EmployeeSkill> findBySkillId(Long skillId);

    @Query("from EmployeeSkill where skillId in :id")
    Iterable<EmployeeSkill> findBySkillId(@Param("id") Iterable<Long> id);
}
