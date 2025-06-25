package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.*;

/**
 * Контроллер для обработки HTTP-запросов, связанных с пользователями.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>(); // Хранилище пользователей в виде HashMap

    @GetMapping // Обрабатывает HTTP GET запросы по пути /users
    public List<User> getAllUsers() {
        // Преобразуем значения Map в ArrayList для возврата
        return new ArrayList<>(users.values());
    }

    @PostMapping // Обрабатывает HTTP POST запросы по пути /users
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя: {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        validateUser(user); // Валидация пользователь согласно задания
        user.setId(getNextId()); // Устанавливаем новый ID перед сохранением
        users.put(user.getId(), user);// Сохраняем пользователя в Map
        log.info("Пользователь успешно создан: {}", user);
        return user; // Возвращаем созданного пользователя
    }

    @PutMapping // Обрабатывает HTTP PUT запросы по пути /users
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя с ID {}: {}", user.getId(), user);
        validateUser(user); // Валидация пользователь согласно задания
        // Проверяем существование пользователя
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с id=" + user.getId() + " не найден");
        }
        // Обновляем данные пользователя
        users.put(user.getId(), user);
        log.info("Пользователь успешно обновлён: {}", user);
        // Возвращаем обновленного пользователя
        return user;
    }

    // Метод генерирует следующий доступный ID для нового пользователя.
    private int getNextId() {
        return users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0) + 1;
    }

    // Метод валидации пользователя согласно задания
    private void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }

}
