package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с хранилищем пользователей.
 * Определяет методы для управления пользователями и их дружескими связями.
 */
public interface UserRepository {
    /**
     * Получение всех пользователей из хранилища
     *
     * @return список всех пользователей
     */
    List<User> findAll();

    /**
     * Поиск пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return Optional с найденным пользователем или пустой, если не найден
     */
    Optional<User> findById(Integer id);

    /**
     * Сохранение нового пользователя
     *
     * @param user объект пользователя для сохранения
     * @return сохраненный пользователь с присвоенным идентификатором
     */
    User save(User user);

    /**
     * Обновление существующего пользователя
     *
     * @param user объект пользователя с обновленными данными
     * @return обновленный пользователь
     */
    User update(User user);

    /**
     * Удаление пользователя по идентификатору
     *
     * @param id идентификатор пользователя для удаления
     */
    void delete(Integer id);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

}
