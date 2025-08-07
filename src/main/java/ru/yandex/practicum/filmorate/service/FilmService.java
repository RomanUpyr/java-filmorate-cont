package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервисный класс для работы с фильмами.
 * Обеспечивает бизнес-логику приложения для операций с фильмами.
 * Взаимодействует с репозиторием
 */
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final UserService userService;
    private final MpaService mpaService;
    private final GenreService genreService;
    private final UserRepository userRepository;

    /**
     * Получает список всех фильмов.
     *
     * @return список объектов {@link Film}
     */
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    /**
     * Находит фильм по его идентификатору.
     *
     * @param id идентификатор фильма
     * @return найденный объект {@link Film}
     * @throws NotFoundException если фильм с указанным id не найден
     */
    public Film findById(Integer id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Film not found with id: " + id));
    }

    /**
     * Создает новый фильм.
     *
     * @param film объект фильма для создания
     * @return созданный объект {@link Film} с присвоенным идентификатором
     */
    public Film create(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        // Проверяем MPA
        mpaService.findById(film.getMpa().getId());

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            checkGenresExist(film.getGenres());
        }

        return filmRepository.save(film);
    }

    private void checkGenresExist(Set<Genre> genres) {
        // Собираем уникальные ID жанров
        Set<Integer> uniqueGenreIds = genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        // Проверяем каждый уникальный ID
        uniqueGenreIds.forEach(genreId -> {
            if (genreService.findById(genreId) == null) {
                throw new NotFoundException("Жанр с ID " + genreId + " не найден");
            }
        });
    }

    /**
     * Обновляет существующий фильм.
     *
     * @param film объект фильма с обновленными данными
     * @return обновленный объект {@link Film}
     * @throws NotFoundException если фильм с указанным id не найден
     */
    public Film update(Film film) {
        findById(film.getId()); // Проверка существования фильма
        return filmRepository.update(film);
    }

    /**
     * Удаляет фильм по идентификатору.
     *
     * @param id идентификатор фильма для удаления
     */
    public void delete(Integer id) {
        filmRepository.delete(id);
    }

    /**
     * Добавляет лайк фильму от пользователя.
     *
     * @param filmId идентификатор фильма
     * @param userId идентификатор пользователя
     * @throws NotFoundException если фильм или пользователь не найдены
     */
    @Transactional
    public void addLike(Integer filmId, Integer userId) {
        // Проверяем существование фильма и пользователя
        findById(filmId);
        userService.findById(userId);

        // Проверяем, не ставил ли уже пользователь лайк
        if (filmRepository.hasLike(filmId, userId)) {
            throw new ValidationException("User " + userId + " already liked film " + filmId);
        }

        // Добавляем лайк
        filmRepository.addLike(filmId, userId);
    }

    /**
     * Удаляет лайк у фильма.
     *
     * @param filmId идентификатор фильма
     * @param userId идентификатор пользователя
     * @throws NotFoundException если фильм или пользователь не найдены
     */
    @Transactional
    public void removeLike(Integer filmId, Integer userId) {
        // Проверяем существование фильма и пользователя
        if (!filmRepository.existsById(filmId)) {
            throw new NotFoundException("Фильм с ID " + filmId + " не найден");
        }
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с ID " + userId + " не найден");
        }

        // Проверяем, ставил ли пользователь лайк
        if (!filmRepository.hasLike(filmId, userId)) {
            throw new NotFoundException("Like from user " + userId + " to film " + filmId + " not found");
        }

        // Удаляем лайк
        filmRepository.removeLike(filmId, userId);
    }

    /**
     * Получает список самых популярных фильмов.
     *
     * @param count количество фильмов в списке
     * @return список объектов {@link Film}, отсортированных по количеству лайков
     */
    public List<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }
}