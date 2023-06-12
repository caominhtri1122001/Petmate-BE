package com.example.petmate.service.comment;

import com.example.petmate.model.request.CommentRequest;
import com.example.petmate.model.response.CommentResponse;

import java.util.List;

public interface CommentService {
	boolean createComment(CommentRequest request);

	List<CommentResponse> getCommentsOfBlog(String postId);
}
