package com.engineer.recognitionSystem;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecognitionSystemApplication {

	@PostConstruct
	public void init() {
		// 打印環境變數
		System.out.println("Environment variable GOOGLE_APPLICATION_CREDENTIALS: "
				+ System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
	}

	public static void main(String[] args) {
		SpringApplication.run(RecognitionSystemApplication.class, args);
	}

}
