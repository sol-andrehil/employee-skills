package com.solstice.skills.solsticeskills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkillController {

    private SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/skills")
    public Iterable<Skill> getSkills(@RequestParam(value = "capability", required = false) String capability) {
        return skillService.getSkills(capability);
    }

    @GetMapping("/skills/{name}")
    public Skill getSkillByName(@PathVariable("name") String name) {
        return skillService.getSkillByName(name);
    }
}
