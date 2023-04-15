package com.example.petmate.service.category;

import com.example.petmate.dto.CategoryDto;
import com.example.petmate.entity.Category;
import com.example.petmate.entity.projector.CategoryProjector;
import com.example.petmate.entity.projector.PostProjector;
import com.example.petmate.mapper.CategoryMapper;
import com.example.petmate.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> getTagsOfPost(String postId) {
		return categoryRepository.findTagsOfPost(UUID.fromString(postId));
	}

	@Override
	public List<Category> getPostOfTags(String tagId) {
		return categoryRepository.findPostsOfTag(UUID.fromString(tagId));
	}

	@Override
	public CategoryDto getAllTagsOfPost(String postId) {
		return CategoryMapper.toCategoryDto(categoryRepository.findAllTagsOfPost(UUID.fromString(postId)));
	}

	@Override
	public List<PostProjector> getAllPostsByTag(String tagId) {
		return categoryRepository.findAllPostsByTag(UUID.fromString(tagId));
	}

}
