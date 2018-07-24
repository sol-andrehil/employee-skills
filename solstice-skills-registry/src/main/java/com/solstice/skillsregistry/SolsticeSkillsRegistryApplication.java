package com.solstice.skillsregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SolsticeSkillsRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolsticeSkillsRegistryApplication.class, args);
    }
}
