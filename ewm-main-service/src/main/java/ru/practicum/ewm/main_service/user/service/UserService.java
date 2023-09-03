package ru.practicum.ewm.main_service.user.service;

import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.model.User;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    List<UserDto> findAllUsers(int from, int size, List<Long> ids);

    void removeUser(Long userId);

    User validateUser(Long userId);
}