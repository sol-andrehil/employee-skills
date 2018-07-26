package com.solstice.skills.solsticeskills;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SkillsTest {

    @Mock
    private SkillRepository skillRepository;

    @Before
    public void before() {
        Skill skill = new Skill();
        skill.setId(1L);
        when(skillRepository.findSkillBySkillName("angular")).thenReturn(skill);
    }

    @Test
    public void getSkills() {
        SkillService skillService = new SkillService(skillRepository);
        Assert.assertNotNull(skillService.getSkills(null));
    }

    @Test
    public void getSkillsByCapability() {
        SkillService skillService = new SkillService(skillRepository);
        Assert.assertNotNull(skillService.getSkills("technical"));
    }

    @Test
    public void getSkillsByName() {
        SkillService skillService = new SkillService(skillRepository);
        Assert.assertNotNull(skillService.getSkillByName("angular"));
    }
}
