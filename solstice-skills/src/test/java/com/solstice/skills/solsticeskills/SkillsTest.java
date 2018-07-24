package com.solstice.skills.solsticeskills;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SkillsTest {

    @Mock
    private SkillRepository skillRepository;

    @Test
    public void getSkills() {
        SkillService skillService = new SkillService(skillRepository);
        Assert.assertNotNull(skillService.getSkills());
    }
}
