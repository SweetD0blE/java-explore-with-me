package ru.practicum.ewm.main_service.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.dto.NewCategoryDto;
import ru.practicum.ewm.main_service.category.model.Category;

@Component
public interface CategoryMapper {
    Category newCategoryDtoToCategory(NewCategoryDto newCategoryDto);

    Category categoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);
}