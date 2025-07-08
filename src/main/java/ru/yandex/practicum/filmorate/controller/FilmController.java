package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmService;

import java.util.List;


/**
 * Контроллер для обработки HTTP-запросов, связанных с фильмами.
 * Данные хранятся в памяти (HashMap) и возвращаются в формате JSON.
 */
@Slf4j
@RestController
@RequestMapping("/films") // Базовый путь для всех эндпоинтов этого контроллера
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получен запрос на получение всех фильмов");
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получен запрос на получение фильма с id={}", id);
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма: {}", film);
        Film createdFilm = filmService.addFilm(film);
        log.debug("Фильм успешно создан: {}", createdFilm);
        return createdFilm;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма с id={}", film.getId());
        Film updatedFilm = filmService.updateFilm(film);
        log.debug("Фильм успешно обновлён: {}", updatedFilm);
        return updatedFilm;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос на добавление лайка фильму {} от пользователя {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос на удаление лайка фильму {} от пользователя {}", id, userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос на получение {} популярных фильмов", count);
        return filmService.getPopularFilms(count);
    }
}
