package com.example.petmate.controller;

import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.CommentRequest;
import com.example.petmate.model.response.CommentResponse;
import com.example.petmate.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@Operation(summary = "api to create comment")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to create comment", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PostMapping
	public boolean createComment(@RequestBody CommentRequest request) throws ResponseException {
		return commentService.createComment(request);
	}

	@Operation(summary = "api to get all comment by postid")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "api to get all comment by postid", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentRequest.class)) }) })
	@GetMapping("/{id}")
	public ResponseEntity<List<CommentResponse>> createComment(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(commentService.getCommentsOfBlog(id));
	}
}
