package ru.practicum.ewm.main_service.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserAdminController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Запрос на добавление пользователя {}", userDto);
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> findAllUsers(@RequestParam(name = "ids", defaultValue = "") List<Long> users,
                                      @RequestParam(name = "from", defaultValue = "0")
                                      @PositiveOrZero int from,
                                      @RequestParam(name = "size", defaultValue = "10")
                                      @Positive int size) {
        log.info("Запрос на поиск всех пользователей");
        return userService.findAllUsers(from, size, users);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Object> removeCategory(@PathVariable("userId") Long userId) {
        log.info("Запрос на удаление пользователя {}", userId);
        userService.removeUser(userId);
        return new ResponseEntity<>("Пользователь удален", HttpStatus.NO_CONTENT);
    }
}