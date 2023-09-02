package ru.practicum.ewm.main_service.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.mapper.CategoryMapper;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.category.repository.CategoryRepository;
import ru.practicum.ewm.main_service.event.repository.EventRepository;
import ru.practicum.ewm.main_service.exception.ConflictException;
import ru.practicum.ewm.main_service.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public Category validateCategory(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new ObjectNotFoundException(String.format(
                "Категория с id = %d не найдена.", catId)));
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new ConflictException(String.format("Category %d : %s уже существует", categoryDto.getId(), categoryDto.getName()));
        }
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void removeCategory(Long catId) {
        Category category =  validateCategory(catId);
        if (eventRepository.findByCategory(category).isPresent()) {
            throw new ConflictException("Нельзя удалить категорию связанную с событием");
        }
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto patchCategory(CategoryDto categoryDto, Long catId) {
        categoryDto.setId(catId);
        Category category = validateCategory(catId);
        if (Objects.equals(categoryDto.getName(), category.getName())) {
            return categoryDto;
        }
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new ConflictException(String.format("Category %d : %s уже существует", catId, categoryDto.getName()));
        }
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAllCategories(int from, int size) {
        int index = from / size;
        PageRequest page = PageRequest.of(index, size);
        return categoryRepository.findAll(page).stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findCategoryById(Long catId) {
        return CategoryMapper.toCategoryDto(validateCategory(catId));
    }
}