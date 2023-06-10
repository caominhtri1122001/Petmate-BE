package com.example.petmate.mapper;

import com.example.petmate.entity.Post;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.PostRequest;
import com.example.petmate.model.response.PostResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface PostMapper {
	static PostResponse toResponse(Post entity, User user) {
		return PostResponse.builder()
				.title(entity.getTitle())
				.author(user.getEmail())
				.views(entity.getViews())
				.likes(entity.getLikes())
				.comments(entity.getComments())
				.build();
	}

	static Post toEntity(PostRequest request, String image) {
		return Post.builder()
				.title(request.getTitle())
				.content(request.getContent())
				.image(image)
				.userId(UUID.fromString(request.getAuthor()))
				.build();
	}
}
