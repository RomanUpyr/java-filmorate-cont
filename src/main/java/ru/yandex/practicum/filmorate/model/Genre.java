package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/**
 * Класс представляет жанр фильма.
 * Содержит идентификатор и название жанра.
 */
@Data
public class Genre {
    private Integer id;       // Уникальный идентификатор жанра
    private String name;  // Название жанра

    /**
     * Конструктор для создания объекта жанра
     *
     * @param id   уникальный идентификатор жанра
     * @param name название жанра
     */
    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
