

package com.MedHeadProject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@SpringBootApplication
@ComponentScan({"controllers", "services", "CustomError"})
@EntityScan("entities")
@EnableJpaRepositories("repositories")
@EnableWebMvc

public class MedHeadProject2Application {

    public static void main(String[] args) {
        SpringApplication.run(MedHeadProject2Application.class, args);
    }

   

}
