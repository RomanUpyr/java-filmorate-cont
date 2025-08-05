package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс представляет статус дружеских отношений между пользователями.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipStatus {
    private int id;             // Уникальный идентификатор статуса
    private String name;        // Название статуса
}

