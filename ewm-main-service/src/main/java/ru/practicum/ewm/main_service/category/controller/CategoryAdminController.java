package ru.practicum.ewm.main_service.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.service.CategoryService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Запрос на создание категории");
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{catId}")
    public ResponseEntity<Object> removeCategory(@PathVariable("catId") Long catId) {
        log.info("Запрос на удаление категории");
        categoryService.removeCategory(catId);
        return new ResponseEntity<>("Категория удалена", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{catId}")
    public CategoryDto patchCategory(@Valid @RequestBody CategoryDto categoryDto,
                                     @PathVariable("catId") Long catId) {
        log.info("Запрос на обновление категории");
        return categoryService.patchCategory(categoryDto, catId);
    }

}