package com.jpa.jpaBlog;

import com.jpa.jpaBlog.core.security.GitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {JpaBlogApplication.class, Jsr310JpaConverters.class})
@EnableCaching
@EnableConfigurationProperties({GitProperties.class})
public class JpaBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBlogApplication.class, args);
	}
}
