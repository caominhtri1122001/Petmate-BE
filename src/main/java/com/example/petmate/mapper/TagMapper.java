package com.example.petmate.mapper;

import com.example.petmate.dto.TagDto;
import com.example.petmate.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TagMapper {

	static List<TagDto> toListTagDto(List<Tag> tags) {
		return tags.stream().map(TagMapper::toTagDto).collect(Collectors.toList());
	}

	static TagDto toTagDto(Tag tag) {
		return TagDto.builder()
				.name(tag.getName()).build();
	}
}
