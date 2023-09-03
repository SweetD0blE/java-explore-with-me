package ru.practicum.ewm.main_service.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAllCategories(@RequestParam(name = "from", defaultValue = "0")
                                               @PositiveOrZero int from,
                                               @RequestParam(name = "size", defaultValue = "10")
                                               @Positive int size) {
        return categoryService.findAllCategories(from,size);
    }

    @GetMapping({"/{catId}"})
    public CategoryDto findCategoryById(@PathVariable("catId") Long catId) {
        return categoryService.findCategoryById(catId);
    }
}