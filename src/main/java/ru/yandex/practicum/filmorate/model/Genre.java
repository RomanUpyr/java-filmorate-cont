package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс представляет жанр фильма.
 * Содержит идентификатор и название жанра.
 */

@AllArgsConstructor
@Getter
@Setter
public class Genre {
    private Integer id;       // Уникальный идентификатор жанра
    private String name;  // Название жанра

}
