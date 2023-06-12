package com.example.petmate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private String postId;
	private String title;
	private String content;
	private String createdAt;
	private String updatedAt;
	private String image;
	private List<String> tags;
	private int views;
	private int comments;
	private int likes;
	private String userId;
	private String userImage;
	private String name;
	private String mail;
	private String phone;
}
