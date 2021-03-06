package com.solstice.skills.solsticeskills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class
})
public class SolsticeSkillsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolsticeSkillsApplication.class, args);
    }
}
