package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.*;

/**
 * Класс представляет возрастной рейтинг фильма по системе MPA (Motion Picture Association).
 * Содержит идентификатор, краткий код и полное описание рейтинга.
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Mpa {
    private Integer id;              // Уникальный идентификатор рейтинга
    private String code;         // Краткий код рейтинга
    private String description;  // Описание возрастных ограничений

    /**
     * Конструктор для создания объекта рейтинга
     *
     * @param id          уникальный идентификатор
     * @param code        краткий код рейтинга
     * @param description описание ограничений
     */
    public Mpa(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    /**
     * Возвращаем code как name, чтобы тесты прошли.
     * И не менять схему базы данных
     */
    @JsonGetter("name")
    public String getName() {
        return this.code;
    }
}
