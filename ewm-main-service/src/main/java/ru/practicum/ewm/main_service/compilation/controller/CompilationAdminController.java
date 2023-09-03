package ru.practicum.ewm.main_service.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main_service.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.main_service.compilation.model.Compilation;
import ru.practicum.ewm.main_service.compilation.service.CompilationServiceImpl;
import ru.practicum.ewm.main_service.exception.ValidationException;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class CompilationAdminController {

    private final CompilationServiceImpl compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto add(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        if (newCompilationDto.getTitle() == null || newCompilationDto.getTitle().isBlank())
            throw new ValidationException("Название подборки не может быть пустым");
        Compilation compilation =
                compilationService.addCompilation(CompilationMapper.toCompilation(newCompilationDto));

        return CompilationMapper.toCompilationDto(compilation);
    }

    @PatchMapping("/{compilationId}")
    public CompilationDto patch(
            @PathVariable Long compilationId, @Valid @RequestBody NewCompilationDto newCompilationDto
    ) {
        Compilation compilation = compilationService
                .updateCompilation(compilationId, CompilationMapper.toCompilation(newCompilationDto));

        return CompilationMapper.toCompilationDto(compilation);
    }

    @DeleteMapping("/{compilationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compilationId) {
        compilationService.removeCompilation(compilationId);
    }
}