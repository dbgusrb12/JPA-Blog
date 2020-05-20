package com.jpa.jpaBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {JpaBlogApplication.class, Jsr310JpaConverters.class})
public class JpaBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBlogApplication.class, args);
	}

}
