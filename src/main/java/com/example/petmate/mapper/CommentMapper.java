package com.example.petmate.mapper;

import com.example.petmate.entity.Comment;
import com.example.petmate.model.request.CommentRequest;
import com.example.petmate.model.response.CommentResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface CommentMapper {
	static Comment toEntity(CommentRequest request) {
		String parentId;
		if(Objects.isNull(request.getCommentId())) {
			parentId = null;
		} else  {
			parentId = request.getCommentId();
		}
		return Comment.builder()
				.content(request.getContent())
				.commentId(UUID.fromString(parentId))
				.userId(UUID.fromString(request.getUserId()))
				.postId(UUID.fromString(request.getPostId()))
				.build();
	}

	static List<CommentResponse> toListResponse(List<Comment> entities) {
		return entities.stream().map(CommentMapper::toResponse).collect(Collectors.toList());
	}

	static CommentResponse toResponse(Comment entity) {
		return CommentResponse.builder()
				.commentId(entity.getId().toString())
				.content(entity.getContent())
				.userId(entity.getUserId().toString())
				.postId(entity.getPostId().toString())
				.parentCommentId(entity.getCommentId().toString())
				.build();
	}
}
