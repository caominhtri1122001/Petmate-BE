package com.example.petmate.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
	private String commentId;
	private String userId;
	private String name;
	private String avatar;
	private String postId;
	private String content;
	private String createdAt;
	private String parentCommentId;
}
