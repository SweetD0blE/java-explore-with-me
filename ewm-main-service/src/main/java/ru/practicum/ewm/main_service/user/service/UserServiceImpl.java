package ru.practicum.ewm.main_service.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.exception.ConflictException;
import ru.practicum.ewm.main_service.exception.ObjectNotFoundException;
import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException(String.format("Пользователя с id = %d не существует", userId)));
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (userRepository.findByName(userDto.getName()).isPresent()) {
            throw new ConflictException(String.format("User %s : %s уже существует", userDto.getName(), userDto.getEmail()));
        }
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers(int from, int size, List<Long> ids) {
        List<UserDto> userDtoList;
        PageRequest page = PageRequest.of(from / size, size);
        if (ids.isEmpty()) {
            userDtoList = userRepository.findAll(page).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
            if (userDtoList.isEmpty()) {
                return Collections.emptyList();
            } else {
                return userDtoList;
            }
        } else {
            userDtoList = userRepository.findByIdIn(ids, page).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        }

        return userDtoList;
    }

    @Override
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }

}