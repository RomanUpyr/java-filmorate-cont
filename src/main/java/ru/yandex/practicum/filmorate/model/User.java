package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий пользователя в системе.
 * Содержит основные данные пользователя и используется для хранения и передачи информации о пользователях.
 */
@Data
@Builder
public class User {
    private Integer id;         // Уникальный идентификатор пользователя

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен содержать символ @")
    private String email;       // Электронная почта пользователя

    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
    private String login;       // Логин пользователя

    private String name;        // Имя пользователя для отображения

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday; // Дата рождения пользователя

    private Map<Integer, FriendshipStatus> friends = new HashMap<>();

    /**
     * Добавляет друга с указанным статусом
     * @param friendId ID пользователя-друга
     * @param status статус дружеских отношений
     */
    public void addFriend(int friendId, FriendshipStatus status) {
        friends.put(friendId, status);
    }

    /**
     * Удаляет друга
     * @param friendId ID пользователя-друга
     */
    public void removeFriend(int friendId) {
        friends.remove(friendId);
    }

    /**
     * Обновляет статус дружбы
     * @param friendId ID пользователя-друга
     * @param status новый статус дружбы
     * @throws IllegalArgumentException если указанный друг не найден
     */
    public void updateFriendshipStatus(int friendId, FriendshipStatus status) {
        if (!friends.containsKey(friendId)) {
            throw new IllegalArgumentException("Друг с ID " + friendId + " не найден");
        }
        friends.put(friendId, status);
    }
}
