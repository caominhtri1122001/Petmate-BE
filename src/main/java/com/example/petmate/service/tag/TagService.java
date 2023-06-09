package com.example.petmate.service.tag;

import com.example.petmate.dto.TagDto;

import java.util.List;

public interface TagService {
	List<TagDto> getAllTags();

	TagDto createTag(String name);

	TagDto getTagById(String tagId);

	boolean deleteTag(String tagId);
}
