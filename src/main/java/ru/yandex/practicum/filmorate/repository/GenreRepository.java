package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с жанрами фильмов.
 */
public interface GenreRepository {
    /**
     * Получает все жанры из базы данных
     *
     * @return список всех жанров, отсортированный по ID
     */
    List<Genre> findAll();

    /**
     * Находит жанр по его идентификатору
     *
     * @param id уникальный идентификатор жанра
     * @return Optional с найденным жанром или empty, если не найден
     */
    Optional<Genre> findById(Integer id);

    /**
     * Получает все жанры для указанного фильма
     *
     * @param filmId идентификатор фильма
     * @return список жанров фильма, отсортированный по ID
     */
    List<Genre> getFilmGenres(Integer filmId);

    /**
     * Добавляет жанр к фильму
     *
     * @param filmId  идентификатор фильма
     * @param genreId идентификатор жанра
     */
    void addGenreToFilm(Integer filmId, Integer genreId);

    /**
     * Удаляет жанр у фильма
     *
     * @param filmId  идентификатор фильма
     * @param genreId идентификатор жанра
     */
    void removeGenreFromFilm(Integer filmId, Integer genreId);
}
