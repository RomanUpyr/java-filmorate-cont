package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;

import java.util.List;

/**
 * Сервисный класс для работы с жанрами фильмов.
 * Обеспечивает доступ к информации о жанрах.
 * Взаимодействует с репозиторием {@link GenreRepository} для доступа к данным.
 */
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    /**
     * Получает список всех жанров.
     *
     * @return список объектов {@link Genre}
     */
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    /**
     * Находит жанр по его идентификатору.
     *
     * @param id идентификатор жанра
     * @return найденный объект {@link Genre}
     * @throws NotFoundException если жанр с указанным id не найден
     */
    public Genre findById(Integer id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found with id: " + id));
    }

    /**
     * Получает список жанров для указанного фильма.
     *
     * @param filmId идентификатор фильма
     * @return список объектов {@link Genre}, связанных с фильмом
     */
    public List<Genre> getFilmGenres(Integer filmId) {
        return genreRepository.getFilmGenres(filmId);
    }
}