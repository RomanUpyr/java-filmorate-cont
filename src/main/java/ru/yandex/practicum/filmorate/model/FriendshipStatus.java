package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/**
 * Класс представляет статус дружеских отношений между пользователями.
 * Содержит идентификатор, название статуса и его описание.
 */
@Data
public class FriendshipStatus {
    private int id;             // Уникальный идентификатор статуса
    private String name;        // Название статуса
    private String description; // Описание статуса

    /**
     * Конструктор для создания объекта статуса дружбы
     * @param id уникальный идентификатор
     * @param name название статуса
     * @param description описание статуса
     */
    public FriendshipStatus(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

