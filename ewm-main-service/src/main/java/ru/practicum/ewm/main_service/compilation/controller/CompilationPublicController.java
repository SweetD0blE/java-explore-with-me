package ru.practicum.ewm.main_service.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.main_service.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(
            @RequestParam(defaultValue = "false") Boolean pinned,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        return compilationService.getCompilations(pinned, from, size)
                .stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @GetMapping("/{compilationId}")
    public CompilationDto getCompilation(@PathVariable Long compilationId) {
        return CompilationMapper.toCompilationDto(compilationService.getCompilation(compilationId));
    }
}