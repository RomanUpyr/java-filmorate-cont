package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendshipStatusRepository;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

/**
 * Контроллер для обработки HTTP-запросов, связанных с пользователями.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final FriendshipStatusRepository statusRepository;
    private final FriendService friendService;

    public UserController(UserService userService, FriendshipStatusRepository statusRepository, FriendService friendService) {
        this.userService = userService;
        this.statusRepository = statusRepository;
        this.friendService = friendService;
    }

    // Основные операции с пользователями
    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получен запрос на получение всех пользователей");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен запрос на получение пользователя с id={}", id);
        return userService.findById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя: {}", user);
        // Если имя не указано, используем логин
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userService.create(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя с ID {}: {}", user.getId(), user);
        return userService.update(user);
    }

    // Расширенные операции с друзьями
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на добавление в друзья: {} -> {}", id, friendId);
        friendService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        return friendService.getFriends(id);
    }

    @GetMapping("/friendship-statuses")
    public List<FriendshipStatus> getAllFriendshipStatuses() {
        return statusRepository.findAll();
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer id,
                                       @PathVariable Integer otherId) {
        return friendService.getCommonFriends(id, otherId);
    }

    @PutMapping("/{id}/friends/{friendId}/confirm")
    public void confirmFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на подтверждение дружбы: {} подтверждает {}", id, friendId);
        friendService.confirmFriendship(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на удаление из друзей: {} удаляет {}", id, friendId);
        friendService.removeFriend(id, friendId);
    }

}

