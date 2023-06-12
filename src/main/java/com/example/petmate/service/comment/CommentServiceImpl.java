package com.example.petmate.service.comment;

import com.example.petmate.mapper.CommentMapper;
import com.example.petmate.model.request.CommentRequest;
import com.example.petmate.model.response.CommentResponse;
import com.example.petmate.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;

	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public boolean createComment(CommentRequest request) {
		commentRepository.save(CommentMapper.toEntity(request));
		return true;
	}

	@Override
	public List<CommentResponse> getCommentsOfBlog(String postId) {
		return CommentMapper.toListResponse(commentRepository.findByPostId(UUID.fromString(postId)));
	}
}
