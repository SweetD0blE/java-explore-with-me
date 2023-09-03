package ru.practicum.ewm.main_service.user.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main_service.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(List<Long> ids, PageRequest pageRequest);

    Optional<User> findByName(String name);
}