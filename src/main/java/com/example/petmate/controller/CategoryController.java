package com.example.petmate.controller;

import com.example.petmate.dto.CategoryDto;
import com.example.petmate.entity.Category;
import com.example.petmate.entity.Post;
import com.example.petmate.entity.Tag;
import com.example.petmate.entity.projector.PostProjector;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(summary = "api to get all tags of post")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all tags of post", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Tag.class)) }) })
	@GetMapping("/getTags")
	public ResponseEntity<List<Category>> getTagsOfPost(String postId) throws ResponseException {
		return ResponseEntity.ok(categoryService.getTagsOfPost(postId));
	}

	@Operation(summary = "api to get all posts of tag")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all posts of tag", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Post.class)) }) })
	@GetMapping("/getPosts")
	public ResponseEntity<List<Category>> getPostsOfTag(String tagId) throws ResponseException {
		return ResponseEntity.ok(categoryService.getPostOfTags(tagId));
	}

	@Operation(summary = "api to get all tags of post")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all tags of post", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDto.class)) }) })
	@GetMapping("/getAllTagsOfPost")
	public ResponseEntity<CategoryDto> getAllTagsOfPost(String postId) throws ResponseException {
		return ResponseEntity.ok(categoryService.getAllTagsOfPost(postId));
	}

	@Operation(summary = "api to get all posts of tag")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all posts of tag", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostProjector.class)) }) })
	@GetMapping("/getAllPostsOfTag")
	public ResponseEntity<List<PostProjector>> getAllPostsOfTag(String tagId) throws ResponseException {
		return ResponseEntity.ok(categoryService.getAllPostsByTag(tagId));
	}
}
