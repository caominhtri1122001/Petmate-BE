package com.example.petmate.service.third_party.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiServiceImpl implements OpenAiService {

	private final RestTemplate restTemplate;
	@Value("${openai.key}")
	private String apiKey;
	@Value("${openai.base-url}")
	private String openAiUrl;

	public OpenAiServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String callChatBot(String context) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject jsonObjectSystem = new JSONObject();
		jsonObjectSystem.put("role", "system");
		jsonObjectSystem.put("content", "You are a helpful assistant.");

		JSONObject jsonObjectUser = new JSONObject();
		jsonObjectUser.put("role","user");
		jsonObjectUser.put("content", "Hello!");

		JSONArray jsonArray = new JSONArray();
		jsonArray.put(jsonObjectSystem);
		jsonArray.put(jsonObjectUser);
		
		String jsonString = new JSONObject().put("model", "gpt-3.5-turbo")
				.put("messages", jsonArray)
				.toString();
		HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
		ResponseEntity<String> response = restTemplate.exchange(openAiUrl, HttpMethod.POST, entity, String.class);
		return response.getBody();
	}
}
