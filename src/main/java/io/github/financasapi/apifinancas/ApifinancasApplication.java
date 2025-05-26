package io.github.financasapi.apifinancas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApifinancasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApifinancasApplication.class, args);
	}

}
