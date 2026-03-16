package ru.kashtanov.just_fire_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JustFireServiceApplication {

	public static void main(String[] args) {
		System.out.println("=== ПРОВЕРКА ПЕРЕМЕННЫХ ОКРУЖЕНИЯ ===");
		System.out.println("POSTGRES_URL: " + System.getenv("POSTGRES_URL"));
		System.out.println("POSTGRES_USER: " + System.getenv("POSTGRES_USER"));
		System.out.println("POSTGRES_PASSWORD: " + (System.getenv("POSTGRES_PASSWORD") != null ? "установлен" : "null"));

		// Проверим system properties
		System.out.println("\n=== SYSTEM PROPERTIES ===");
		System.out.println("POSTGRES_URL: " + System.getProperty("POSTGRES_URL"));


		SpringApplication.run(JustFireServiceApplication.class, args);
	}

}
