package com.learn.SidClasses;

import com.learn.SidClasses.Customs_Constants.AppConstants;
import com.learn.SidClasses.Exception.ResourceAlreadyExistException;
import com.learn.SidClasses.Exception.ResourceNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;


@SpringBootApplication
public class SidClassesApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SidClassesApplication.class, args);
	}
}
