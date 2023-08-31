package ru.practicum.ewm.main_service.compilation.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main_service.compilation.model.Compilation;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.model.Event;

import java.util.List;

@Component
public interface CompilationMapper {

    Compilation newDtoToCompilation(NewCompilationDto newCompilationDto, List<Event> events);

    CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> eventsShortDto);
}