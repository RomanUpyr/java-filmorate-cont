package ru.yandex.practicum.filmorate.storage;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // Основные операции с пользователями
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(int id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }
        return user;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(@NotNull User user) {
        getUserById(user.getId()); // Проверка существования пользователя
        return userStorage.updateUser(user);
    }

    // Операции с друзьями
    public void sendFriendRequest(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);

        user.getFriends().put(friendId, new FriendshipStatus(1, "REQUESTED", "Запрос отправлен"));
        friend.getFriends().put(userId, new FriendshipStatus(2, "PENDING", "Запрос ожидает подтверждения"));
    }

    public void confirmFriendship(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);

        user.getFriends().put(friendId, new FriendshipStatus(3, "CONFIRMED", "Дружба подтверждена"));
        friend.getFriends().put(userId, new FriendshipStatus(3, "CONFIRMED", "Дружба подтверждена"));
    }

    public void rejectFriendship(int userId, int friendId) {
        removeFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getFriendsByStatus(int userId, String statusName) {
        User user = getUserById(userId);
        return user.getFriends().entrySet().stream()
                .filter(entry -> entry.getValue().getName().equals(statusName))
                .map(Map.Entry::getKey)
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        User user = getUserById(userId);
        User otherUser = getUserById(otherUserId);

        return user.getFriends().keySet().stream()
                .filter(friendId -> otherUser.getFriends().containsKey(friendId))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }
}

