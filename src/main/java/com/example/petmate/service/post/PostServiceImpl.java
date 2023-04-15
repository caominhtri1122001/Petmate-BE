package com.example.petmate.service.post;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.entity.Category;
import com.example.petmate.entity.Post;
import com.example.petmate.entity.Tag;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.PostMapper;
import com.example.petmate.model.request.PostRequest;
import com.example.petmate.model.response.PostResponse;
import com.example.petmate.repository.CategoryRepository;
import com.example.petmate.repository.PostRepository;
import com.example.petmate.repository.TagRepository;
import com.example.petmate.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	private final TagRepository tagRepository;

	private final CategoryRepository categoryRepository;

	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, TagRepository tagRepository,
			CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@Override
	public PostResponse createPost(PostRequest request) {
		Post post = postRepository.save(PostMapper.toEntity(request));
		Optional<User> user = userRepository.findById(post.getUserId());
		List<Tag> tags = tagRepository.findAll();
		request.getTags().forEach(tag -> {
			Tag tagExists = tags.stream().filter(t -> t.getName().equalsIgnoreCase(tag)).findFirst().orElse(null);
			if (tagExists != null) {
				createCategory(post.getId(), tagExists.getId());
			} else {
				Tag tagEntity = Tag.builder().name(tag).build();
				tagRepository.save(tagEntity);
				createCategory(post.getId(), tagEntity.getId());
			}
		});
		return PostMapper.toResponse(post, user.get());
	}

	@Override
	public boolean updatePost(String postId, PostRequest request) {
		Optional<Post> post = postRepository.findById(UUID.fromString(postId));
		if (post.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		post.get().setTitle(request.getTitle());
		post.get().setContent(request.getContent());
		post.get().setImage(request.getImage());
		postRepository.save(post.get());
		return true;
	}

	@Override
	public Post getPostById(String postId) {
		Optional<Post> post = postRepository.findById(UUID.fromString(postId));
		if (post.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		return post.get();
	}

	@Override
	public boolean deletePost(String postId) {
		Optional<Post> post = postRepository.findById(UUID.fromString(postId));
		if (post.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		postRepository.deleteById(UUID.fromString(postId));
		return true;
	}

	private void createCategory(UUID postId, UUID tagId) {
		categoryRepository.save(Category.builder().postId(postId).tagId(tagId).build());
	}
}
