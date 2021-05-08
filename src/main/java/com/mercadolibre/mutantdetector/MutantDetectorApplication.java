package com.mercadolibre.mutantdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@SpringBootApplication
public class MutantDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutantDetectorApplication.class, args);
	}

}
