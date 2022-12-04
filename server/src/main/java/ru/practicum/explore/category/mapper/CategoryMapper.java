package ru.practicum.explore.category.mapper;

import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.dto.NewCategoryDto;
import ru.practicum.explore.category.model.Category;

import java.util.HashSet;

public class CategoryMapper {

    public static Category toCategory (NewCategoryDto newCategoryDto) {
        return new Category(null, newCategoryDto.getName(), new HashSet<>());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
