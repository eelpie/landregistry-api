package uk.co.eelpieconsulting.landregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import uk.co.eelpieconsulting.landregistry.controllers.CORSHandler;

@EnableAutoConfiguration
@ComponentScan
@Configuration
@EnableScheduling
@EnableWebMvc
public class Main extends WebMvcConfigurerAdapter {
	
	private static ApplicationContext ctx;
    
    public static void main(String[] args) throws Exception {
    	ctx = SpringApplication.run(Main.class, args);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new CORSHandler());       
    }
    
    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setDispatchOptionsRequest(true);
        return servlet;
    }
    
}
