package ru.practicum.explore.category.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.dto.NewCategoryDto;
import ru.practicum.explore.category.mapper.CategoryMapper;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.category.repository.ICategoryRepository;
import ru.practicum.explore.exceptionhandler.ConflictException;
import ru.practicum.explore.exceptionhandler.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService implements ICategoryService {

    private final ICategoryRepository iCategoryRepository;

    public CategoryService(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return CategoryMapper.toCategoryDto(iCategoryRepository.save(CategoryMapper.toCategory(newCategoryDto)));
    }

    @Override
    public List<CategoryDto> getAll(List<Integer> ids, int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);

        List<Category> categoriesList;

        if (!ids.isEmpty()) {
            categoriesList = iCategoryRepository.findByIdIn(ids, pageable).getContent();
        } else {
            categoriesList = iCategoryRepository.findAll(pageable).getContent();
        }

        return categoriesList.stream().map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        Integer categoryId = categoryDto.getId();

        Optional<Category> categoryToUpdate = iCategoryRepository.findById(categoryId);

        if (categoryToUpdate.isEmpty()) {
            log.error("NotFoundException: {}", "При обновлении, Категория с ИД " + categoryId + " не найдена");
            throw new NotFoundException("Категория с ИД " + categoryId + " не найдена");
        }

        if (categoryDto.getName() != null) {

            Optional<Category> tempCategory = iCategoryRepository.findByName(categoryDto.getName());

            if (iCategoryRepository.findByName(categoryDto.getName()).isPresent()) {
                log.error("ConflictException: {}", "Категория : " + categoryDto.getName() + " уже существует");
                throw new ConflictException("Категория : " + categoryDto.getName() + " уже существует");
            }

            categoryToUpdate.get().setName(categoryDto.getName());
        }

        return CategoryMapper.toCategoryDto(iCategoryRepository.save(categoryToUpdate.get()));
    }

    @Override
    public HttpStatus deleteCategory(int categoryId) {
        if (iCategoryRepository.findById(categoryId).isEmpty()) {
            log.error("NotFoundException: {}", "При удалении, Категория с ИД " + categoryId + " не найдена");
            throw new NotFoundException("Категория с ИД " + categoryId + " не найдена");
        }
        iCategoryRepository.deleteById(categoryId);
        return HttpStatus.OK;
    }

    @Override
    public Optional<Category> getCategory(int categoryId) {
        return iCategoryRepository.findById(categoryId);
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {

        Optional<Category> category = iCategoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            log.error("NotFoundException: {}", "При удалении, Категория с ИД " + categoryId + " не найдена");
            throw new NotFoundException("Категория с ИД " + categoryId + " не найдена");
        }

        return CategoryMapper.toCategoryDto(category.get());
    }
}
