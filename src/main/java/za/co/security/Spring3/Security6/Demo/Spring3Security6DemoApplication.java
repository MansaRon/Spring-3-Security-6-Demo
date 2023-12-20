package za.co.security.Spring3.Security6.Demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import za.co.security.Spring3.Security6.Demo.controllers.AuthController;

@SpringBootApplication
public class Spring3Security6DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring3Security6DemoApplication.class, args);
	}

}
