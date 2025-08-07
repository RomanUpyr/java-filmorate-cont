package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с хранилищем фильмов.
 * Определяет основные CRUD-операции и специфичные методы для работы с фильмами.
 */
public interface FilmRepository {
    /**
     * Получение всех фильмов из хранилища
     *
     * @return список всех фильмов
     */
    List<Film> findAll();

    /**
     * Поиск фильма по идентификатору
     *
     * @param id идентификатор фильма
     * @return Optional с найденным фильмом или пустой, если не найден
     */
    Optional<Film> findById(Integer id);

    /**
     * Сохранение нового фильма
     *
     * @param film объект фильма для сохранения
     * @return сохраненный фильм с присвоенным идентификатором
     */
    Film save(Film film);

    /**
     * Обновление существующего фильма
     *
     * @param film объект фильма с обновленными данными
     * @return обновленный фильм
     */
    Film update(Film film);

    /**
     * Удаление фильма по идентификатору
     *
     * @param id идентификатор фильма для удаления
     */
    void delete(Integer id);

    /**
     * Добавление лайка фильму от пользователя
     *
     * @param filmId идентификатор фильма
     * @param userId идентификатор пользователя
     */
    void addLike(Integer filmId, Integer userId);

    /**
     * Удаление лайка у фильма
     *
     * @param filmId идентификатор фильма
     * @param userId идентификатор пользователя
     */
    void removeLike(Integer filmId, Integer userId);

    /**
     * Получение списка популярных фильмов
     *
     * @param count количество возвращаемых фильмов
     * @return список фильмов, отсортированных по количеству лайков
     */
    List<Film> getPopularFilms(int count);

    boolean hasLike(Integer filmId, Integer userId);

    boolean existsById(Integer filmId);
}
