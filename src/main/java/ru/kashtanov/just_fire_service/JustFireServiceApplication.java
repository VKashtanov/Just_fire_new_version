package ru.kashtanov.just_fire_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JustFireServiceApplication {

	public static void main(String[] args) {
		System.out.println("=== ПРОВЕРКА ПЕРЕМЕННЫХ ОКРУЖЕНИЯ ===");
		System.out.println("INCOMAND_URL: " + System.getenv("INCOMAND_URL"));
		System.out.println("INCOMAND_USERNAME: " + System.getenv("INCOMAND_USERNAME"));
		System.out.println("INCOMAND_PASSWORD: " + (System.getenv("INCOMAND_PASSWORD")));

		// Проверим system properties
		System.out.println("\n=== SYSTEM PROPERTIES ===");
		System.out.println("POSTGRES_URL: " + System.getProperty("POSTGRES_URL"));


		SpringApplication.run(JustFireServiceApplication.class, args);
	}

}
