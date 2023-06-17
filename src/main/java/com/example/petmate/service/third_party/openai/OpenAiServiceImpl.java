package com.example.petmate.service.third_party.openai;

import com.example.petmate.dto.OpenAiModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Slf4j
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
		jsonObjectSystem.put("content",
				"As a pet assistant, please only answer questions related to pets. If the user asks something not related to pets, reply that you don't know it.");

		JSONObject jsonObjectUser = new JSONObject();
		jsonObjectUser.put("role", "user");
		jsonObjectUser.put("content", context);

		JSONArray jsonArray = new JSONArray();
		jsonArray.put(jsonObjectSystem);
		jsonArray.put(jsonObjectUser);

		String jsonString = new JSONObject().put("model", "gpt-3.5-turbo")
				.put("messages", jsonArray)
				.put("temperature", 0.5)
				.put("max_tokens", 50)
				.put("n", 1)
				.put("stream", false)
				.toString();
		HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
		ResponseEntity<String> response = restTemplate.exchange(openAiUrl, HttpMethod.POST, entity, String.class);
//		Gson gson = new Gson();
//		OpenAiModel result = gson.fromJson(response.getBody(), OpenAiModel.class);
//		return result.getChoices().get(0).getMessage().getContent();
		return response.getBody();
	}
}
