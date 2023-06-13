package com.example.petmate.mapper;

import com.example.petmate.entity.Comment;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.CommentRequest;
import com.example.petmate.model.response.CommentResponse;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface CommentMapper {
	static Comment toEntity(CommentRequest request) {
		UUID parentId;
		if(Objects.isNull(request.getCommentId())) {
			parentId = null;
		} else  {
			parentId = UUID.fromString(request.getCommentId());
		}
		return Comment.builder()
				.content(request.getContent())
				.commentId(parentId)
				.userId(UUID.fromString(request.getUserId()))
				.postId(UUID.fromString(request.getPostId()))
				.build();
	}

	static CommentResponse toResponse(Comment entity, User userEntity) {
		String parentCommentId;
		if(Objects.isNull(entity.getCommentId())) {
			parentCommentId = null;
		} else {
			parentCommentId = entity.getCommentId().toString();
		}
		return CommentResponse.builder()
				.commentId(entity.getId().toString())
				.content(entity.getContent())
				.userId(entity.getUserId().toString())
				.name(userEntity.getFirstName() + " " + userEntity.getLastName())
				.avatar(userEntity.getUserImgUrl())
				.postId(entity.getPostId().toString())
				.parentCommentId(parentCommentId)
				.createdAt(entity.getCreatedAt().toString())
				.build();
	}
}
