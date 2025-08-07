package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendRepository;
import ru.yandex.practicum.filmorate.repository.FriendshipStatusRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendshipStatusRepository statusRepository;

    @Transactional
    public void addFriend(Integer userId, Integer friendId) {
        log.debug("Attempting to add friend: {} -> {}", userId, friendId);

        // 1. Базовая валидация
        validateUsers(userId, friendId);

        // 2. Проверка существующих отношений
        checkExistingFriendship(userId, friendId);

        // 3. Создание заявки в друзья
        createFriendshipRequest(userId, friendId);

        log.info("Friend request created: {} -> {}", userId, friendId);
    }

    private void checkExistingFriendship(Integer userId, Integer friendId) {
        Optional<Integer> existingStatus = friendRepository.getFriendshipStatus(userId, friendId);
        Optional<Integer> reverseStatus = friendRepository.getFriendshipStatus(friendId, userId);

        if (existingStatus.isPresent() || reverseStatus.isPresent()) {
            String message = "Friendship relation already exists: " + userId + " and " + friendId;
            if (existingStatus.isPresent()) {
                message += " (status: " + getStatusName(existingStatus.get()) + ")";
            }
            if (reverseStatus.isPresent()) {
                message += " (reverse status: " + getStatusName(reverseStatus.get()) + ")";
            }
            throw new ValidationException(message);
        }
    }

    private String getStatusName(int statusId) {
        return statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalStateException("Status not found: " + statusId))
                .getName();
    }

    private void createFriendshipRequest(Integer userId, Integer friendId) {
        Integer pendingStatusId = statusRepository.findByName("PENDING")
                .orElseThrow(() -> new IllegalStateException("PENDING status not found"))
                .getId();

        friendRepository.addFriend(userId, friendId, pendingStatusId);
    }

    @Transactional
    public void confirmFriendship(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);

        Integer pendingStatusId = getStatusId("PENDING");
        if (!friendRepository.hasPendingRequest(friendId, userId)) {
            throw new ValidationException("No pending friendship request found");
        }

        Integer confirmedStatusId = statusRepository.findByName("CONFIRMED")
                .orElseThrow(() -> new IllegalStateException("CONFIRMED status not found"))
                .getId();

        friendRepository.updateFriendshipStatus(friendId, userId, confirmedStatusId);
        friendRepository.updateFriendshipStatus(userId, friendId, confirmedStatusId);
    }

    @Transactional(readOnly = true)
    public List<User> getFriends(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));

        // Получаем только подтверждённых друзей
        return friendRepository.getFriendsByUserId(userId).stream()
                .filter(Objects::nonNull)
                .peek(friend -> {
                    if (friend.getId() == null) {
                        throw new IllegalStateException("Friend has null ID");
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFriend(Integer userId, Integer friendId) {
        validateUsers(userId, friendId);
        friendRepository.removeFriend(userId, friendId);
    }

    @Transactional(readOnly = true)
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        validateUsers(userId, otherId);
        Integer confirmedStatusId = getStatusId("CONFIRMED");
        return friendRepository.findCommonFriends(userId, otherId);
    }

    private void validateUsers(Integer userId, Integer friendId) {
        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("User cannot add themselves as friend");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + friendId));
    }

    private Integer getStatusId(String statusName) {
        return statusRepository.findByName(statusName)
                .orElseThrow(() -> new IllegalStateException(statusName + " status not found"))
                .getId();
    }
}
