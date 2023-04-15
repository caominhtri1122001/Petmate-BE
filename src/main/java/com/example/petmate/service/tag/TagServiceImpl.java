package com.example.petmate.service.tag;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.dto.TagDto;
import com.example.petmate.entity.Tag;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.TagMapper;
import com.example.petmate.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;

	public TagServiceImpl(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	@Override
	public List<TagDto> getAllTags() {
		return TagMapper.toListTagDto(tagRepository.findAll());
	}

	@Override
	public TagDto createTag(String name) {
		Tag tag = new Tag(name);
		tagRepository.save(tag);
		return TagMapper.toTagDto(tag);
	}

	@Override
	public TagDto getTagById(String tagId) {
		Optional<Tag> tag = tagRepository.findById(UUID.fromString(tagId));
		if (tag.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		return TagMapper.toTagDto(tag.get());
	}

	@Override
	public boolean deleteTag(String tagId) {
		Optional<Tag> tag = tagRepository.findById(UUID.fromString(tagId));
		if (tag.isPresent()) {
			tagRepository.deleteById(UUID.fromString(tagId));
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		return true;
	}
}
