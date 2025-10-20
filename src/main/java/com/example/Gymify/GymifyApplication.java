package com.example.Gymify;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Gimify API", version = "1.0",description = "API REST pentru managementul utilizatorilor, antrenamentelor și exercițiilor din aplicația de fitness Gymify."))
@SpringBootApplication
public class GymifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymifyApplication.class, args);
	}

}
