package com.example.petmate.controller;

import com.example.petmate.service.third_party.openai.OpenAiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai")
public class OpenAIController {

	private final OpenAiService openAiService;

	public OpenAIController(OpenAiService openAiService) {
		this.openAiService = openAiService;
	}

	@Operation(summary = "api to openai")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to openai", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PostMapping
	public String callOpenAi(@RequestBody String context) throws JsonProcessingException {
		return openAiService.callChatBot(context);
	}
}
