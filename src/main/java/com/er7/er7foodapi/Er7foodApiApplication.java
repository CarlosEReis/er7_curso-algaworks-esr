package com.er7.er7foodapi;

import com.er7.er7foodapi.infrastructure.repository.CustoJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustoJpaRepositoryImpl.class)
public class Er7foodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Er7foodApiApplication.class, args);
	}

}
