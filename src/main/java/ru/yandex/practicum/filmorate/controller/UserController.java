package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserService;

import java.util.List;

/**
 * Контроллер для обработки HTTP-запросов, связанных с пользователями.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Основные операции с пользователями
    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получен запрос на получение всех пользователей");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен запрос на получение пользователя с id={}", id);
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя: {}", user);
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя с ID {}: {}", user.getId(), user);
        return userService.updateUser(user);
    }

    // Расширенные операции с друзьями
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на добавление в друзья: {} -> {}", id, friendId);
        userService.sendFriendRequest(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}/confirm")
    public void confirmFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на подтверждение дружбы: {} подтверждает {}", id, friendId);
        userService.confirmFriendship(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на удаление из друзей: {} удаляет {}", id, friendId);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получен запрос на получение друзей пользователя {}", id);
        return userService.getFriendsByStatus(id, "CONFIRMED");
    }

    @GetMapping("/{id}/friends/pending")
    public List<User> getPendingFriends(@PathVariable int id) {
        log.info("Получен запрос на получение ожидающих подтверждения друзей {}", id);
        return userService.getFriendsByStatus(id, "PENDING");
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос на общих друзей {} и {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }
}

