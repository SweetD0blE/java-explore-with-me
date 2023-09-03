package ru.practicum.ewm.main_service.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.compilation.model.Compilation;
import ru.practicum.ewm.main_service.compilation.repository.CompilationRepository;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.repository.EventRepository;
import ru.practicum.ewm.main_service.exception.ObjectNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public Compilation addCompilation(Compilation compilation) {
        if (compilation.getEvents() != null && !compilation.getEvents().isEmpty())
            compilation.setEvents(new HashSet<>(eventRepository.findAllByIdIn(compilation.getEvents().stream()
                    .map(Event::getId)
                    .collect(Collectors.toList()))));
        return compilationRepository.save(compilation);
    }

    @Override
    public void removeCompilation(Long compilationId) {
        Compilation compilation = findCompilation(compilationId);
        compilationRepository.delete(compilation);
    }

    @Override
    public Compilation updateCompilation(Long compilationId, Compilation compilation) {
        Compilation currentCompilation = findCompilation(compilationId);
        if (compilation.getTitle() != null)
            currentCompilation.setTitle(compilation.getTitle());
        if (compilation.getPinned() != null)
            currentCompilation.setPinned(compilation.getPinned());
        if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
            List<Event> events = eventRepository.findAllByIdIn(
                    compilation.getEvents().stream().map(Event::getId).collect(Collectors.toList())
            );
            currentCompilation.setEvents(new HashSet<>(events));
        }

        return compilationRepository.save(currentCompilation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compilation> getCompilations(boolean pinned, int from, int size) {
        PageRequest page = PageRequest.of(from / size, size);
        return compilationRepository.findByPinned(pinned, page);
    }

    @Override
    @Transactional(readOnly = true)
    public Compilation getCompilation(Long compilationId) {
        return findCompilation(compilationId);
    }

    private Compilation findCompilation(Long compilationId) {
        return compilationRepository.findById(compilationId)
                .orElseThrow(() -> new ObjectNotFoundException("Подборка не найдена"));
    }
}