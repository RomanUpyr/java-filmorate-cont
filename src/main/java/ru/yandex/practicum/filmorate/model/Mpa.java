package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/**
 * Класс представляет возрастной рейтинг фильма по системе MPA (Motion Picture Association).
 * Содержит идентификатор, краткий код и полное описание рейтинга.
 */
@Data
public class Mpa {
    private int id;              // Уникальный идентификатор рейтинга
    private String name;         // Краткий код рейтинга
    private String description;  // Описание возрастных ограничений

    /**
     * Конструктор для создания объекта рейтинга
     * @param id уникальный идентификатор
     * @param name краткий код рейтинга
     * @param description описание ограничений
     */
    public Mpa(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
