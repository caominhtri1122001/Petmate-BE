package com.example.petmate.controller;

import com.example.petmate.dto.TagDto;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.service.tag.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tags")
public class TagController extends BaseController {

	private final TagService tagService;

	public TagController(TagService tagService) {
		this.tagService = tagService;
	}

	@Operation(summary = "api to get all tags")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all tags", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TagDto.class)) }) })
	@GetMapping
	public ResponseEntity<List<TagDto>> getAllTags(HttpServletRequest request) throws ResponseException {
		String userId = checkUser(request);
		log.info(userId);
		return ResponseEntity.ok(tagService.getAllTags());
	}

	@Operation(summary = "api to create tag")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to create tag", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TagDto.class)) }) })
	@PostMapping
	public ResponseEntity<TagDto> createTag(final String name) throws ResponseException {
		return ResponseEntity.ok(tagService.createTag(name));
	}

	@Operation(summary = "api to get tag by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get tag by id", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TagDto.class)) }) })
	@GetMapping(path = "/{id}")
	public ResponseEntity<TagDto> getTagById(final String id) throws ResponseException {
		return ResponseEntity.ok(tagService.getTagById(id));
	}

	@Operation(summary = "api to delete tag")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to delete tag", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean> deleteTag(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(tagService.deleteTag(id));
	}
}
