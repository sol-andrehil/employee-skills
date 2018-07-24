package com.solstice.skills.solsticeskills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    private SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Iterable<Skill> getSkills(String capability) {
        if (capability != null) {
            return skillRepository.findByCapability(capability);
        }
        return skillRepository.findAll();
    }

    public Skill getSkillByName(String name) {
        return skillRepository.findSkillBySkillName(name);
    }
}
