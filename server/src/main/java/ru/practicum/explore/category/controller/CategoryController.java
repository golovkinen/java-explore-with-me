package ru.practicum.explore.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.dto.NewCategoryDto;
import ru.practicum.explore.category.service.ICategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Validated
public class CategoryController {
    private final ICategoryService iCategoryService;

    public CategoryController(ICategoryService iCategoryService) {
        this.iCategoryService = iCategoryService;
    }


    @GetMapping(value = "/categories")
    public List<CategoryDto> getAll(@RequestParam(name = "id", required = false) Optional<List<Integer>> ids,
                                    @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") int from,
                                    @Positive @RequestParam(name = "size", defaultValue = "10") int size) {
        return iCategoryService.getAll(ids.orElse(Collections.emptyList()), from, size);
    }

    @GetMapping(value = "/categories/{catId}")
    public CategoryDto getCategoryById(@Positive @PathVariable(name = "catId") int id) {
        return iCategoryService.getCategoryById(id);
    }


    @PostMapping(value = "/admin/categories")
    public CategoryDto create(@Valid @RequestBody NewCategoryDto newCategoryDto) {

        return iCategoryService.createCategory(newCategoryDto);
    }

    @PatchMapping(value = "/admin/categories")
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {

        return iCategoryService.updateCategory(categoryDto);
    }

    @DeleteMapping(value = "/admin/categories/{catId}")
    public HttpStatus delete(@Positive @PathVariable(name = "catId") int id) {
        return iCategoryService.deleteCategory(id);
    }
}
