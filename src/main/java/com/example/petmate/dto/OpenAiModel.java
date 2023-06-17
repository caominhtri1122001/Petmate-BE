package com.example.petmate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiModel {
	public String id;
	public String object;
	public int created;
	public String model;
	public Usage usage;
	public ArrayList<Choice> choices;


	@Getter
	@Setter
	@SuperBuilder(toBuilder = true)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Choice{
		public Message message;
		public String finish_reason;
		public int index;
	}


	@Getter
	@Setter
	@SuperBuilder(toBuilder = true)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Message{
		public String role;
		public String content;
	}

	@Getter
	@Setter
	@SuperBuilder(toBuilder = true)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Usage{
		public int prompt_tokens;
		public int completion_tokens;
		public int total_tokens;
	}
}
