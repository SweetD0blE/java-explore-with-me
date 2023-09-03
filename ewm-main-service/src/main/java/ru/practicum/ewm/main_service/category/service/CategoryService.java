package ru.practicum.ewm.main_service.category.service;

import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.model.Category;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    void removeCategory(Long catId);

    CategoryDto patchCategory(CategoryDto categoryDto, Long catId);

    List<CategoryDto> findAllCategories(int from, int size);

    CategoryDto findCategoryById(Long catId);

    Category validateCategory(Long catId);
}