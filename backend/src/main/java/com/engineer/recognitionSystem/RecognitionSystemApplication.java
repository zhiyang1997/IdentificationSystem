package com.engineer.recognitionSystem;

import java.io.File;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class RecognitionSystemApplication {

	@PostConstruct
	public void init() throws Exception {

		try {
			// 從 resources 資料夾中將密鑰檔案提取到TempFile
			File tempFile = File.createTempFile("google-key", ".json");
			Files.copy(new ClassPathResource("google/key.json").getInputStream(), tempFile.toPath(),
					java.nio.file.StandardCopyOption.REPLACE_EXISTING);

			// 設置 GOOGLE_APPLICATION_CREDENTIALS 環境變數
			System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", tempFile.getAbsolutePath());
			System.out.println("GOOGLE_APPLICATION_CREDENTIALS set to: " + tempFile.getAbsolutePath());
		} catch (Exception e) {
			throw new Exception("OCR初始化失敗" , e);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(RecognitionSystemApplication.class, args);
	}

}
