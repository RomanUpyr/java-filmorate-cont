package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с дружескими связями между пользователями.
 * Управляет отношениями "дружба" и их статусами.
 */
public interface FriendRepository {
    /**
     * Добавляет запись о дружбе между пользователями
     *
     * @param userId   идентификатор пользователя-инициатора
     * @param friendId идентификатор пользователя-друга
     */
    void addFriend(Integer userId, Integer friendId, Integer statusId);

    /**
     * Удаляет запись о дружбе между пользователями
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор друга
     */
    void removeFriend(Integer userId, Integer friendId);

    /**
     * Проверяет существование дружеской связи
     *
     * @param userId   идентификатор первого пользователя
     * @param friendId идентификатор второго пользователя
     * @return true если связь существует, иначе false
     */

    boolean friendshipExists(int userId, int friendId);

    /**
     * Подтверждает дружбу между пользователями
     *
     * @param userId   идентификатор первого пользователя
     * @param friendId идентификатор второго пользователя
     */
    void confirmFriendship(Integer userId, Integer friendId);

    Optional<Integer> getFriendshipStatus(int userId, int friendId);

    List<User> findFriendsByStatus(Integer userId, String statusName);

    boolean hasPendingRequest(Integer userId, Integer friendId);


    void updateFriendshipStatus(Integer userId, Integer friendId, Integer newStatusId);

    List<User> findCommonFriends(Integer userId, Integer otherId);


    List<User> getFriends(Integer userId);

    List<User> getFriendsByStatus(Integer userId, String statusName);

    List<User> getCommonFriends(Integer userId, Integer otherId);

    List<User> getFriendsByUserId(Integer userId);
}
