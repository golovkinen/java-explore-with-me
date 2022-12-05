package ru.practicum.explore.category.service;

import org.springframework.http.HttpStatus;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.dto.NewCategoryDto;
import ru.practicum.explore.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    List<CategoryDto> getAll(List<Integer> ids, int from, int size);

    CategoryDto updateCategory(CategoryDto categoryDto);

    HttpStatus deleteCategory(int categoryId);

    Optional<Category> getCategory(int categoryId);

    CategoryDto getCategoryById(int categoryId);
}
