package com.solstice.skills.solsticeskills;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {
    Iterable<Skill> findByCapability(String capability);

    Skill findSkillBySkillName(String name);
}
