package com.example.petmate.controller;

import com.example.petmate.entity.Post;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.PostRequest;
import com.example.petmate.model.response.PostResponse;
import com.example.petmate.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@Operation(summary = "api to get all posts")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all posts", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Post.class)) }) })
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts() throws ResponseException {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	@Operation(summary = "api to creat post")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to create post", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostRequest.class)) }) })
	@PostMapping
	public ResponseEntity<PostResponse> createPost(@ModelAttribute PostRequest request) throws ResponseException, IOException {
		return ResponseEntity.ok(postService.createPost(request));
	}

	@Operation(summary = "api to update post")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to update post", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostRequest.class)) }) })
	@PatchMapping("/{id}")
	public Boolean updatePost(@PathVariable String id, @ModelAttribute PostRequest request) throws ResponseException, IOException {
		return postService.updatePost(id,request);
	}

	@Operation(summary = "api to find post by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to find post by id", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/{id}")
	public ResponseEntity<Post> getPost(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	@Operation(summary = "api to delete post")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to delete post", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@DeleteMapping("/{id}")
	public Boolean deletePost(@PathVariable String id) throws ResponseException {
		return postService.deletePost(id);
	}
}
