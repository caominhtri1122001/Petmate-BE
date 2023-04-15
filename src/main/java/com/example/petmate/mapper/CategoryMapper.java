package com.example.petmate.mapper;

import com.example.petmate.dto.CategoryDto;
import com.example.petmate.entity.projector.CategoryProjector;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CategoryMapper {
	static CategoryDto toCategoryDto(List<CategoryProjector> categoryProjectorList) {
		List<String> tags = categoryProjectorList.stream().map(CategoryProjector::getTagName).collect(Collectors.toList());
		return CategoryDto.builder()
				.postId(categoryProjectorList.get(0).getPostId())
				.title(categoryProjectorList.get(0).getPostTitle())
				.tags(tags)
				.build();
	}
}
