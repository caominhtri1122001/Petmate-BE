package com.example.petmate.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

	@PostConstruct
	public void initializeFirebase() throws IOException {
		FileInputStream serviceAccount = new FileInputStream("C:\\Users\\Cao Minh Tri\\IdeaProjects\\petmate\\src\\main\\resources\\serviceAccountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(
				GoogleCredentials.fromStream(serviceAccount)).build();

		FirebaseApp.initializeApp(options);

	}

	@Bean
	public Storage storage() {
		StorageOptions storageOptions = StorageOptions.getDefaultInstance();
		return storageOptions.getService();
	}
}
