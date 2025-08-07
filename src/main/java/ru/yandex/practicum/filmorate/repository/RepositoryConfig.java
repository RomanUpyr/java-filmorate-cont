package ru.yandex.practicum.filmorate.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Конфигурационный класс для настройки репозиториев.
 */
@Configuration
public class RepositoryConfig {

    /**
     * Создание бина репозитория для работы с фильмами.
     *
     * @param jdbcTemplate    JdbcTemplate для выполнения SQL-запросов
     * @param genreRepository репозиторий для работы с жанрами
     * @return реализация FilmRepository
     */
    @Bean
    public FilmRepository filmRepository(JdbcTemplate jdbcTemplate,
                                         GenreRepository genreRepository,
                                         MpaRepository mpaRepository) {
        return new JdbcFilmRepository(jdbcTemplate, genreRepository, mpaRepository);
    }

    /**
     * Создание бина репозитория для работы с пользователями.
     *
     * @param jdbcTemplate     JdbcTemplate для выполнения SQL-запросов
     * @param statusRepository
     * @return реализация UserRepository
     */
    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate, FriendshipStatusRepository statusRepository) {
        return new JdbcUserRepository(jdbcTemplate, statusRepository);
    }

    /**
     * Создание бина репозитория для работы с дружескими связями.
     *
     * @param jdbcTemplate     JdbcTemplate для выполнения SQL-запросов
     * @param statusRepository репозиторий для работы со статусами дружбы
     * @return реализация FriendRepository
     */
    @Bean
    public FriendRepository friendRepository(JdbcTemplate jdbcTemplate,
                                             FriendshipStatusRepository statusRepository,
                                             UserRepository userRepository) {
        return new JdbcFriendRepository(jdbcTemplate, statusRepository, userRepository);
    }
}
