package ru.practicum.ewm.main_service.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main_service.compilation.model.Compilation;
import ru.practicum.ewm.main_service.event.mapper.EventMapper;
import ru.practicum.ewm.main_service.event.model.Event;

import java.util.Collections;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned() != null ? compilation.getPinned() : false)
                .title(compilation.getTitle())
                .events(compilation.getEvents() != null ? compilation.getEvents().stream()
                        .map(EventMapper::toEventShortDto)
                        .collect(Collectors.toList()) : Collections.emptyList()
                )
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        Compilation.CompilationBuilder compilationBuilder =
                Compilation.builder()
                        .pinned(newCompilationDto.getPinned() != null ? newCompilationDto.getPinned() : false)
                        .title(newCompilationDto.getTitle());

        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty())
            compilationBuilder.events(
                    newCompilationDto.getEvents()
                            .stream().map(eventId -> Event.builder().id(eventId).build())
                            .collect(Collectors.toSet())
            );
        return compilationBuilder.build();
    }
}