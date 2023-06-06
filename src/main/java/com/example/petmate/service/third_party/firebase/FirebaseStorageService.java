package com.example.petmate.service.third_party.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FirebaseStorageService {

	private Storage storage;

	@EventListener
	public void init(ApplicationReadyEvent event) {
		try {
			String fileName = "serviceAccountKey.json";
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream serviceAccount = classloader.getResourceAsStream(fileName);
			storage = StorageOptions.newBuilder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setProjectId("petmate-ad592")
					.build()
					.getService();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String uploadImage(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		String uniqueFileName = generateUniqueFileName(fileName);
		Map<String, String> map = new HashMap<>();
		map.put("firebaseStorageDownloadTokens", uniqueFileName);

		BlobId blobId = BlobId.of("petmate-ad592.appspot.com", uniqueFileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType(file.getContentType()).build();
		Blob blob = storage.create(blobInfo, file.getBytes());
		return "https://firebasestorage.googleapis.com/v0/b/petmate-ad592.appspot.com/o/" + blob.getName() + "?alt=media";
	}

	private String generateUniqueFileName(String originalFileName) {
		return UUID.randomUUID() + "-" + originalFileName;
	}
}
