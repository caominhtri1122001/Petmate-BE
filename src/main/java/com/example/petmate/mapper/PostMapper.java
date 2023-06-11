package com.example.petmate.mapper;

import com.example.petmate.dto.PostDto;
import com.example.petmate.entity.Post;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.PostRequest;
import com.example.petmate.model.response.PostResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

	static PostDto toDto(Post entity, User userEntity, List<String> tags) {
		return PostDto.builder()
				.postId(entity.getId().toString())
				.title(entity.getTitle())
				.content(entity.getContent())
				.createdAt(entity.getCreatedAt().toString())
				.updatedAt(entity.getUpdatedAt().toString())
				.image(entity.getImage())
				.tags(tags)
				.views(entity.getViews())
				.comments(entity.getComments())
				.likes(entity.getLikes())
				.userId(entity.getUserId().toString())
				.name(userEntity.getFirstName() + " " + userEntity.getLastName())
				.mail(userEntity.getEmail())
				.phone(userEntity.getPhone())
				.build();
	}
}
