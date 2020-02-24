package com.rabo.filevalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.rabo.filevalidator.raboservice.RaboFileProperties;

@SpringBootApplication
@EnableConfigurationProperties({ RaboFileProperties.class })
public class FileValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileValidatorApplication.class, args);
	}

}
