package ru.practicum.ewm.main_service.compilation.service;

import ru.practicum.ewm.main_service.compilation.model.Compilation;

import java.util.List;

public interface CompilationService {

    Compilation addCompilation(Compilation compilation);

    void removeCompilation(Long compilationId);

    Compilation updateCompilation(Long compilationId, Compilation compilation);


    List<Compilation> getCompilations(boolean pinned, int from, int size);

    Compilation getCompilation(Long compilationId);
}