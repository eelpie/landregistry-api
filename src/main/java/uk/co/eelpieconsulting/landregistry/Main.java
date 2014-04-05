package uk.co.eelpieconsulting.landregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@ComponentScan
@Configuration
@EnableScheduling
public class Main {
	
	private static ApplicationContext ctx;
    
    public static void main(String[] args) throws Exception {
    	ctx = SpringApplication.run(Main.class, args);
    }
    
}