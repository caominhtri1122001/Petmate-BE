package com.example.petmate.service.post;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.dto.PostDto;
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
import com.example.petmate.service.third_party.firebase.FirebaseStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	private final TagRepository tagRepository;

	private final CategoryRepository categoryRepository;

	private final FirebaseStorageService firebaseStorageService;

	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, TagRepository tagRepository,
			CategoryRepository categoryRepository, FirebaseStorageService firebaseStorageService) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
		this.categoryRepository = categoryRepository;
		this.firebaseStorageService = firebaseStorageService;
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(post -> {
			Optional<User> user = userRepository.findById(post.getUserId());
			List<String> tags = new ArrayList<>();
			List<Category> tagsOfBlogs = categoryRepository.findByPostId(post.getId());
			tagsOfBlogs.forEach(tag -> {
				tags.add(tagRepository.findById(tag.getTagId()).get().getName());
			});
			return PostMapper.toDto(post, user.get(), tags);
		}).collect(Collectors.toList());
	}

	@Override
	public PostResponse createPost(PostRequest request) throws IOException {
		String image = "";
		if (request.getImage() != null) {
			image = firebaseStorageService.uploadImage(request.getImage());
		}
		Post post = postRepository.save(PostMapper.toEntity(request, image));
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
	public boolean updatePost(String postId, PostRequest request) throws IOException {
		String image = "";
		if (request.getImage() != null) {
			image = firebaseStorageService.uploadImage(request.getImage());
		}
		Optional<Post> post = postRepository.findById(UUID.fromString(postId));
		if (post.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		post.get().setTitle(request.getTitle());
		post.get().setContent(request.getContent());
		if (image != "") {
			post.get().setImage(image);
		}
		postRepository.save(post.get());

		List<Category> categories = categoryRepository.findTagsOfPost(UUID.fromString(postId));
		if (!categories.isEmpty()){
			categories.forEach(category -> {
				categoryRepository.deleteById(category.getId());
			});
		}

		List<Tag> tags = tagRepository.findAll();
		request.getTags().forEach(tag -> {
			Tag tagExists = tags.stream().filter(t -> t.getName().equalsIgnoreCase(tag)).findFirst().orElse(null);
			if (tagExists != null) {
				createCategory(post.get().getId(), tagExists.getId());
			} else {
				Tag tagEntity = Tag.builder().name(tag).build();
				tagRepository.save(tagEntity);
				createCategory(post.get().getId(), tagEntity.getId());
			}
		});

		return true;
	}

	@Override
	public PostDto getPostById(String postId) {
		List<String> tags = new ArrayList<>();
		Optional<Post> post = postRepository.findById(UUID.fromString(postId));
		if (post.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		post.get().setViews(post.get().getViews() + 1);
		postRepository.save(post.get());
		Optional<User> user = userRepository.findById(post.get().getUserId());
		if (user.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		List<Category> tagsOfBlogs = categoryRepository.findByPostId(post.get().getId());
		tagsOfBlogs.forEach(tag -> {
			tags.add(tagRepository.findById(tag.getTagId()).get().getName());
		});
		return PostMapper.toDto(post.get(), user.get(), tags);
	}

	@Override
	public boolean deletePost(String postId) {
		Optional<Post> post = postRepository.findById(UUID.fromString(postId));
		if (post.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		List<Category> categories = categoryRepository.findTagsOfPost(UUID.fromString(postId));
		if (!categories.isEmpty()){
			categories.forEach(category -> {
				categoryRepository.deleteById(category.getId());
			});
		}

		postRepository.deleteById(UUID.fromString(postId));
		return true;
	}

	private void createCategory(UUID postId, UUID tagId) {
		categoryRepository.save(Category.builder().postId(postId).tagId(tagId).build());
	}
}
