package com.example.petmate.controller;

import com.example.petmate.service.third_party.firebase.FirebaseStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FirebaseController {

	private final FirebaseStorageService firebaseStorageService;

	public FirebaseController(FirebaseStorageService firebaseStorageService) {
		this.firebaseStorageService = firebaseStorageService;
	}

	@PostMapping("/api/v1/firebase")
	public String create(@RequestParam(name = "file") MultipartFile file) throws IOException {
		return firebaseStorageService.uploadImage(file);
	}
}
