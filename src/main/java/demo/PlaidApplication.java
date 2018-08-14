package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class PlaidApplication { // extends SpringBootServletInitializer {

    public static void main(String... args) {
        SpringApplication.run(PlaidApplication.class);
    }

    // runs TomCat8 - server
//    @Override
//    protected SpringApplicationBuilder configure (SpringApplicationBuilder application){
//        return application.sources(PlaidApplication.class);
//    }

}

// TO RUN LOCALLY:
// comment out the following to run locally:
// server info in application, properties.
// tomcat8 dependency in pom file.
// extends SpringBoot... and @Override method above.