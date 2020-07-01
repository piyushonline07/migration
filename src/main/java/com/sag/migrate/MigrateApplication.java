package com.sag.migrate;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@SpringBootApplication
@Controller
@Configuration
public class MigrateApplication extends WebMvcConfigurerAdapter {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
        .addResourceLocations("static/")
        .resourceChain(true)
        .addResolver(new PathResourceResolver() {
        	 @Override
        	    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        	        Resource requestedResource = location.createRelative(resourcePath);     
        	        Resource defaultResource=location.createRelative("index.html");
        	        Resource resource = requestedResource.exists() && requestedResource.isReadable() ? requestedResource : defaultResource;
        	        return resource;
        	    }
        });
	}
	public static void main(String[] args) {
		SpringApplication.run(MigrateApplication.class, args);
	}

	@GetMapping("/home")
	public ModelAndView getJSPModel() {
		ModelAndView view = new ModelAndView("/jsp/index.jsp");
		return view;
	}
	 
	@GetMapping("/angular")
	public ModelAndView getAngularView() {
		ModelAndView view = new ModelAndView("/static/index.html");
		return view;
	}
}
