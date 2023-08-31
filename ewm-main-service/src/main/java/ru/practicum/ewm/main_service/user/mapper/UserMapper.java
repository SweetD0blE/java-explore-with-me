package ru.practicum.ewm.main_service.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.main_service.user.dto.NewUserRequest;
import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.dto.UserShortDto;
import ru.practicum.ewm.main_service.user.model.User;

@Component
public interface UserMapper {
    User toUser(NewUserRequest newUserRequest);

    UserDto toUserDto(User user);

    UserShortDto toUserShortDto(User user);
}