package com.example.petmate.service.comment;

import com.example.petmate.entity.Comment;
import com.example.petmate.entity.Post;
import com.example.petmate.entity.User;
import com.example.petmate.mapper.CommentMapper;
import com.example.petmate.model.request.CommentRequest;
import com.example.petmate.model.response.CommentResponse;
import com.example.petmate.repository.CommentRepository;
import com.example.petmate.repository.PostRepository;
import com.example.petmate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;

	private PostRepository postRepository;

	private UserRepository userRepository;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
			UserRepository userRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	public boolean createComment(CommentRequest request) {
		commentRepository.save(CommentMapper.toEntity(request));
		Optional<Post> post = postRepository.findById(UUID.fromString(request.getPostId()));
		post.ifPresent(value -> value.setComments(value.getComments() + 1));
		postRepository.save(post.get());
		return true;
	}

	@Override
	public List<CommentResponse> getCommentsOfBlog(String postId) {
		List<CommentResponse> result = new ArrayList<>();
		List<Comment> comments = commentRepository.findByPostId(UUID.fromString(postId));
		comments.forEach(comment -> {
			Optional<User> user = userRepository.findById(comment.getUserId());
			result.add(CommentMapper.toResponse(comment, user.get()));
		});
		return result;
	}
}
