package com.example.petmate.service.third_party.openai;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OpenAiService {
	String callChatBot(String context) throws JsonProcessingException;
}
